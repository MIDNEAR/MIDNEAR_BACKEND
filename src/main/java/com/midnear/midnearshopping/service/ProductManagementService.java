package com.midnear.midnearshopping.service;

import com.midnear.midnearshopping.domain.dto.FileDto;
import com.midnear.midnearshopping.domain.dto.category.CategoryDto;
import com.midnear.midnearshopping.domain.dto.products.*;
import com.midnear.midnearshopping.domain.dto.shipping_returns.ShippingReturnsDto;
import com.midnear.midnearshopping.domain.vo.category.CategoryVo;
import com.midnear.midnearshopping.domain.vo.products.ProductColorsVo;
import com.midnear.midnearshopping.domain.vo.products.ProductImagesVo;
import com.midnear.midnearshopping.domain.vo.products.ProductsVo;
import com.midnear.midnearshopping.domain.vo.products.SizesVo;
import com.midnear.midnearshopping.domain.vo.shipping_returns.ShippingReturnsVo;
import com.midnear.midnearshopping.mapper.Category.CategoriesMapper;
import com.midnear.midnearshopping.mapper.ShippingReturns.ShippingReturnsMapper;
import com.midnear.midnearshopping.mapper.products.ProductColorsMapper;
import com.midnear.midnearshopping.mapper.products.ProductImagesMapper;
import com.midnear.midnearshopping.mapper.products.ProductsMapper;
import com.midnear.midnearshopping.mapper.products.SizesMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductManagementService {
    private final CategoriesMapper categoriesMapper;
    private final ProductsMapper productsMapper;
    private final ProductColorsMapper productColorsMapper;
    private final SizesMapper sizesMapper;
    private final ProductImagesMapper productImagesMapper;
    private final ShippingReturnsMapper shippingReturnsMapper;
    private final S3Service s3Service;

    public List<CategoryDto> getCategories() {
        List<CategoryVo> categoryVoList = categoriesMapper.getCategories();

        //vo -> dto로 변환하여 Map에 저장 (부모 카테고리를 빠르게 찾으려고)
        Map<Long, CategoryDto> dtoMap = new HashMap<>();
        for (CategoryVo vo : categoryVoList) {
            dtoMap.put(vo.getCategoryId(), new CategoryDto(vo.getCategoryId(), vo.getCategoryName()));
        }

        // 카테고리 계층 만들기
        List<CategoryDto> rootCategories = new ArrayList<>(); // 최상위 카테고리 list
        for (CategoryVo vo : categoryVoList) {
            CategoryDto current = dtoMap.get(vo.getCategoryId());
            if (vo.getParentCategoryId() == null) {
                // 최상위 카테고리
                rootCategories.add(current);
            } else {
                // 하위 카테고리를 부모의 children 리스트에 추가
                CategoryDto parent = dtoMap.get(vo.getParentCategoryId());
                if (parent != null) {
                    parent.getChildren().add(current);
                }
            }
        }

        return rootCategories;
    }

    @Transactional
    public void registerProducts(ProductsDto productDto) {
        // 공통 상품 정보 저장하고 productId 반환
        ProductsVo productsVo = ProductsVo.builder()
                .productName(productDto.getProductName())
                .price(productDto.getPrice())
                .discountPrice(productDto.getDiscountPrice())
                .discountRate(productDto.getDiscountRate())
                .discountStartDate(productDto.getDiscountStartDate())
                .discountEndDate(productDto.getDiscountEndDate())
                .detail(productDto.getDetail())
                .sizeGuide(productDto.getSizeGuide())
                .registeredDate(productDto.getRegisteredDate())
                .categoryId(productDto.getCategoryId())
                .build();
        productsMapper.registerProducts(productsVo);

        // 컬러별 상품 등록
        List<ProductColorsDto> colors = productDto.getColors();
        for (ProductColorsDto color : colors) {
            // 색상 등록 하고 productColorId 반환
            ProductColorsVo productColorsVo = ProductColorsVo.builder()
                    .color(color.getColor())
                    .productId(productsVo.getProductId())
                    .build();
            productColorsMapper.registerProducts(productColorsVo);
            // 색상별 사이즈 등록
            List<SizesVo> sizes = color.getSizes()
                    .stream()
                    .map(SizesVo::toEntity)
                    .toList();
            for (SizesVo size : sizes) {
                SizesVo sizesVo = SizesVo.builder()
                        .size(size.getSize())
                        .stock(size.getStock())
                        .productColorId(productColorsVo.getProductColorId())
                        .build();
                sizesMapper.registerProducts(sizesVo);
            }
            // 이미지 등록
            // 1. 버킷에 저장
            List<FileDto> fileInfo;
            try {
                fileInfo = s3Service.uploadFiles("product",color.getProductImages());
            } catch (Exception e) {
                throw new RuntimeException("파일 업로드 중 오류가 발생했습니다.", e);
            }

            //2. DB에 이미지 정보 저장
            try {
                for (FileDto file : fileInfo) {
                    ProductImagesVo imagesVo = ProductImagesVo.builder()
                            .productImageId(null)
                            .imageUrl(file.getFileUrl())
                            .fileSize(file.getFileSize())
                            .extension(file.getExtension())
                            .imageCreatedDate(null)
                            .productColorId(productColorsVo.getProductColorId())
                            .build();
                    productImagesMapper.uploadProductImage(imagesVo);
                }

            } catch (Exception e) {
                // S3에 이미 업로드된 파일 삭제
                for (FileDto file : fileInfo) {
                    s3Service.deleteFile(file.getFileUrl());
                }
                e.printStackTrace();
                throw new RuntimeException("이미지 정보를 데이터베이스에 저장하는 중 오류가 발생했습니다.", e);
            }
        }
    }

    public List<ProductManagementListDto> getProductList(int page, int size, String sortOrder, String dateRange, String searchRange, String searchText) {
        List<ProductManagementListDto> productList = new ArrayList<>();

        if (searchText == null || searchText.isBlank()) {
            searchText = null; // 검색 안 하고 필터링만 하는 경우
        } else if (!isValidSearchRange(searchRange)) { // 잘못된 검색 범위가 들어온 경우 검색x
            searchText = null;
        }

        int offset = (page - 1) * size;
        String orderBy = sortOrder.equals("최신순") ? "DESC" : "ASC";

        // 페이지에 맞는 상품 불러오기
        // 1. 정렬만 해서 불러오는 경우
        // 2. 정렬 + 검색(상품명)
        // 3. 정렬 + 검색(판매상태)
        // 4. 정렬 + 등록일시
        // 5. 정렬 + 카테고리

        List<ProductsVo> productsVoList = new ArrayList<>();
        if (searchRange.equals("상품명") || searchRange.equals("등록일시")) {
            productsVoList = productsMapper.getProductPaging(offset, size, orderBy, dateRange, searchRange, searchText);
        } else if (searchRange.equals("판매상태")) {
            searchText = switchText(searchText); // row 값에 맞게 변환
            productsVoList = productsMapper.getProductsBySaleStatus(offset, size, orderBy, dateRange, searchRange, searchText);
        }
        for (ProductsVo product : productsVoList) {
            List<ProductColorsListDto> colors = new ArrayList<>();
            // 1. 부모 카테고리까지 찾아서 문자열로 변환
            String category = getCategoryName(product.getCategoryId());

            // 2. 색상 정보 찾기
            List<ProductColorsVo> productColorsVos = productColorsMapper.searchingProductColorsByProductId(product.getProductId(), searchRange, searchText);
            for (ProductColorsVo productColorsVo : productColorsVos) {
                // 3. 사이즈 정보 찾기
                List<SizesDto> sizes = sizesMapper.getSizesByProductColorsId(productColorsVo.getProductColorId())
                        .stream()
                        .map(SizesDto::toDto)
                        .toList();
                // 색상 + 사이즈 묶기
                ProductColorsListDto productColors = ProductColorsListDto.builder()
                        .color(productColorsVo.getColor())
                        .sizes(sizes)
                        .saleStatus(productColorsVo.getSaleStatus())
                        .build();
                colors.add(productColors);
            }
            // List<ProductManagementListDto>에 add
            ProductManagementListDto productManagementListDto = ProductManagementListDto.builder()
                    .productId(product.getProductId())
                    .category(category)
                    .productName(product.getProductName())
                    .price(product.getPrice())
                    .discountPrice(product.getDiscountPrice())
                    .discountRate(product.getDiscountRate())
                    .colorsList(colors)
                    .registerdDate(product.getRegisteredDate())
                    .build();
            productList.add(productManagementListDto);
        }
        return productList;
    }

    // 검색 범위 제한
    private boolean isValidSearchRange(String searchRange) {
        List<String> validColumns = Arrays.asList("상품명", "판매상태", "카테고리", "등록일시");
        return validColumns.contains(searchRange);
    }
    // 컬럼명으로 변환
    private String switchText(String searchText) {
        switch (searchText) {
            case "판매중":
                return "ON_SALE";
            case "품절":
                return "OUT_OF_STOCK";
            case "숨김":
                return "HIDDEN";
            default:
                return null;
        }
    }

    public String getCategoryName(Long categoryId) {
        return getCategoryHierarchy(categoryId, "");
    }

    public String getCategoryHierarchy(Long categoryId, String hierarchy) {
        CategoryVo category = categoriesMapper.getCategoryById(categoryId);
        if (category == null) {
            return hierarchy;
        }

        // 현재 카테고리 이름을 문자열에 추가
        hierarchy = category.getCategoryName() + (hierarchy.isEmpty() ? "" : " - ") + hierarchy;

        // 부모 카테고리가 있으면, 부모 카테고리 정보를 재귀적으로 호출
        if (category.getParentCategoryId() != null) {
            return getCategoryHierarchy(category.getParentCategoryId(), hierarchy);
        } else {
            return hierarchy;
        }
    }

    @Transactional
    public void setOnSale(List<Long> onSaleList) {
        if (onSaleList == null || onSaleList.isEmpty()) {
            throw new IllegalArgumentException("선택된 상품이 없습니다.");
        }
        productColorsMapper.setOnSale(onSaleList);
    }

    @Transactional
    public void setSoldOut(List<Long> soldOutList) {
        if (soldOutList == null || soldOutList.isEmpty()) {
            throw new IllegalArgumentException("선택된 상품이 없습니다.");
        }
        productColorsMapper.setSoldOut(soldOutList);
    }

    @Transactional
    public void setDiscontinued(List<Long> discontinuedList) {
        if (discontinuedList == null || discontinuedList.isEmpty()) {
            throw new IllegalArgumentException("선택된 상품이 없습니다.");
        }
        productColorsMapper.setDiscontinued(discontinuedList);
    }

    @Transactional
    public void deleteProducts(List<Long> deleteList) {
        // 관련 이미지 삭제
        for (Long id : deleteList) {
            // 버킷에서 삭제
            List<ProductImagesVo> noticeImagesVoList = productImagesMapper.getImagesById(id);
            for (ProductImagesVo imagesVo : noticeImagesVoList) {
                s3Service.deleteFile(imagesVo.getImageUrl());
            }
        }
        // 상품 삭제, 관련 테이블 (productColors, sizes, productImages 에 cascade 걸려있음)
        productsMapper.deleteProducts(deleteList);
    }

    public ProductsDto getProduct(Long productId) {
        ProductsVo productsVo = productsMapper.getProductById(productId); // 상품 정보
        ProductsDto product = ProductsDto.toDto(productsVo);

        List<ProductColorsVo> productColorsVoList = productColorsMapper.getProductColorsByProductId(productId); // 색상별 정보 (검색X)
        for (ProductColorsVo productColorsVo : productColorsVoList) {
            List<SizesDto> sizesVoList = sizesMapper.getSizesByProductColorsId(productColorsVo.getProductColorId())
                    .stream()
                    .map(SizesDto::toDto)
                    .toList(); // 색상별 사이즈 정보 조회
            ProductColorsDto productColorsDto = ProductColorsDto.toDto(productColorsVo);
            productColorsDto.setSizes(sizesVoList); // 색상별 사이즈 정보 넣고
            product.getColors().add(productColorsDto); // 상품에 색상 담기

            List<ProductImagesVo> productImagesVos = productImagesMapper.getImagesById(productColorsVo.getProductColorId());
            for (ProductImagesVo productImagesVo : productImagesVos) {
                productColorsDto.getImageUrls().add(productImagesVo.getImageUrl());
            }
        }
        return product;
    }

    @Transactional
    public void modifyProduct(ProductsDto productsDto) throws Exception {
        // 공통 정보 수정
        ProductsVo productsVo = ProductsVo.toEntity(productsDto);
        productsMapper.updateProduct(productsVo);

        // 색상별 정보 수정
        List<ProductColorsDto> productColorsDtoList = productsDto.getColors();
        for (ProductColorsDto productColorsDto : productColorsDtoList) {
            productColorsDto.setProductId(productsVo.getProductId());
            ProductColorsVo productColorsVo = ProductColorsVo.toEntity(productColorsDto);

            if (productColorsDto.getProductColorId() == null) { // 1. 색상이 추가된 경우
                productColorsMapper.registerProducts(productColorsVo);
            } else { // 2. 색상 정보가 수정된 경우
                productColorsMapper.updateProductColor(productColorsVo);
            }
            // 사이즈 정보 수정
            List<SizesDto> sizesDtoList = productColorsDto.getSizes();
            for (SizesDto sizesDto : sizesDtoList) {
                sizesDto.setProductColorId(productColorsVo.getProductColorId());
                SizesVo sizesVo = SizesVo.toEntity(sizesDto);
                // 1. 사이즈가 추가된 경우
                if (sizesDto.getSizeId() == null) {
                    sizesMapper.registerProducts(sizesVo);
                } else { // 2. 사이즈가 수정된 경우
                    sizesMapper.updateSize(sizesVo);
                }
            }

            // 이미지 삭제 후 재업로드
            try {
                updateImages(productColorsVo.getProductColorId(), productColorsDto.getProductImages());
            } catch (Exception e) {
                throw e;
            }
        }
    }

    // 이미지 삭제 후 재업로드
    @Transactional
    public void updateImages(Long productColorId, List<MultipartFile> images) throws Exception {
        List<FileDto> fileInfo = null;
        try {
            // 기존 이미지 삭제
            List<ProductImagesVo> productImagesVoList = productImagesMapper.getImagesById(productColorId);
            for (ProductImagesVo imagesVo : productImagesVoList) {
                s3Service.deleteFile(imagesVo.getImageUrl());
            }

            // 새로운 파일 업로드
            fileInfo = s3Service.uploadFiles("product", images);

            // DB에 이미지 정보 변경
            productImagesMapper.deleteProductImages(productColorId);
            for (FileDto file : fileInfo) {
                ProductImagesVo imagesVo = ProductImagesVo.builder()
                        .imageUrl(file.getFileUrl())
                        .fileSize(file.getFileSize())
                        .extension(file.getExtension())
                        .imageCreatedDate(null)
                        .productColorId(productColorId)
                        .build();
                productImagesMapper.uploadProductImage(imagesVo);
            }

        } catch (S3Exception s3Ex) {
            throw new RuntimeException("S3 처리 중 오류가 발생했습니다.", s3Ex);
        } catch (Exception ex) {
            if (fileInfo != null) {
                for (FileDto file : fileInfo) {
                    s3Service.deleteFile(file.getFileUrl());
                }
            }
            throw new RuntimeException("DB 업데이트 중 오류가 발생했습니다", ex);
        }
    }

    @Transactional
    public void deleteColors(List<Long> deleteColorsIds) throws Exception {
        // 색상 삭제
        if (deleteColorsIds != null && !deleteColorsIds.isEmpty()) {
            for (Long id : deleteColorsIds) {
                productColorsMapper.deleteColors(Collections.singletonList(id));
            }
        }
    }

    @Transactional
    public void deleteSizes(List<Long> deleteSizesIds) throws Exception {
        if (deleteSizesIds != null && !deleteSizesIds.isEmpty()) {
            for (Long id : deleteSizesIds) {
                sizesMapper.deleteSize(id);
            }
        }
    }

    @Transactional
    public ShippingReturnsVo getShippingReturnsVo() {
        return shippingReturnsMapper.getShippingReturns();
    }

    @Transactional
    public void updateShippingReturns(ShippingReturnsDto shippingReturnsDto) {
        ShippingReturnsVo shippingReturnsVo = ShippingReturnsVo.toEntity(shippingReturnsDto);
        shippingReturnsMapper.updateShippingReturns(shippingReturnsVo);
    }

    @Transactional
    public void updateShippingPolicy(ShippingReturnsDto shippingReturnsDto) {
        ShippingReturnsVo shippingReturnsVo = ShippingReturnsVo.toEntity(shippingReturnsDto);
        shippingReturnsMapper.updateShippingPolicy(shippingReturnsVo);
    }

}


