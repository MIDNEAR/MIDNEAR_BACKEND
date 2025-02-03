package com.midnear.midnearshopping.service;

import com.midnear.midnearshopping.domain.dto.FileDto;
import com.midnear.midnearshopping.domain.dto.category.CategoryDto;
import com.midnear.midnearshopping.domain.dto.coordinate.ColorDto;
import com.midnear.midnearshopping.domain.dto.coordinate.CoordinatedProductDto;
import com.midnear.midnearshopping.domain.dto.coordinate.CoordinateDto;
import com.midnear.midnearshopping.domain.dto.coordinate.MainProductDto;
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

import java.sql.Date;
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
    int size = 23; // 상품 관리에서 페이지에 들어가는 상품 개수 (코디 상품 조회 개수 제한은 지역 변수로 관리함)

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
            if (vo.getParentCategoryId() == null) { // 부모 카테고리가 null이면 최상위 카테고리
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
        // 공통 상품 정보 저장하고 vo에 productId 반환
        ProductsVo productsVo = ProductsVo.toEntity(productDto);
        productsMapper.registerProducts(productsVo);

        // 컬러별 상품 등록
        List<ProductColorsDto> colors = productDto.getColors();
        for (ProductColorsDto color : colors) {
            // 색상 등록 하고 vo에 productColorId 반환
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

    public Map<String, Object> getProductList(int page, String sortOrder, String dateRange, String searchRange, String searchText) {
        Map<String, Object> result = new HashMap<>();
        List<ProductManagementListDto> productList = new ArrayList<>();

        int offset = (page - 1) * size;
        String orderBy = sortOrder.equals("최신순") ? "DESC" : "ASC";

        // 페이지에 맞는 상품 불러오기
        // 1. 정렬만 해서 불러오는 경우
        // 2. 정렬 + 검색(상품명)
        // 3. 정렬 + 검색(판매상태)
        // 4. 정렬 + 등록일시
        // 5. 정렬 + 카테고리
        List<ProductsVo> productsVoList = new ArrayList<>();
        if (searchRange.equals("판매상태")) {
            productsVoList = productsMapper.getProductsBySaleStatus(offset, size, orderBy, dateRange, searchRange, searchText);
        } else if (searchRange.equals("카테고리")) {
            // 카테고리 id 찾고 해당하는 products 찾기
            List<Long> categories = categoriesMapper.getCategoryIdByCategoryName(searchText);
            if (!categories.isEmpty())
                productsVoList = productsMapper.getProductsByCategoryIds(categories);
        } else { // searchRange가 상품명 or 등록일시
            productsVoList = productsMapper.getProductPaging(offset, size, orderBy, dateRange, searchRange, searchText);
        }

        // 상품이 없는 경우
        if (productsVoList.isEmpty()) {
            result.put("totalPageSize", 1);
            result.put("productList", null);
            return result;
        }
        Long pageSize = (long) (productsVoList.size() / size + 1);
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
        result.put("totalPageSize", pageSize);
        result.put("productList", productList);
        return result;
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
        // color 다 삭제 되면기본 정보 거기도 삭제
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

    public Map<String, Object> getCoordinatedList(int page, String sortOrder, String dateRange, String searchRange, String searchText) {
        Map<String, Object> result = new HashMap<>();
        List<MainProductDto> productList = new ArrayList<>();
        int size = 4;
        int offset = (page - 1) * size;
        String orderBy = sortOrder.equals("최신순") ? "DESC" : "ASC";

        List<ProductsVo> productsVoList = new ArrayList<>();
        if (searchRange.equals("카테고리")) {
            // 카테고리 id 찾고 해당하는 products 찾기
            List<Long> categories = categoriesMapper.getCategoryIdByCategoryName(searchText);
            if (!categories.isEmpty())
                productsVoList = productsMapper.getProductsByCategoryIds(categories);
        } else if (searchRange.equals("연관상품") || searchRange.equals("연관상품 등록 일시")) { // 연관상품 이름으로 검색
            // coordinated_product_id에 해당하는 상품 이름으로 검색
            List<Long> coordinatedProductIds = productsMapper.getProductColorsIdsByNameOrDate(offset, size, orderBy, dateRange, searchRange, searchText);
            if (!coordinatedProductIds.isEmpty()) {
                List<Long> originalProductIds = productsMapper.getOriginalProductProductIdsByCoordinatedIds(coordinatedProductIds);
                if (!originalProductIds.isEmpty()) {
                    productsVoList = productsMapper.getProductsByIds(originalProductIds);
                }
            }
        }  else { // searchRange가 상품명 or 등록일시
            productsVoList = productsMapper.getProductPaging(offset, size, orderBy, dateRange, searchRange, searchText);
        }

        Long pageSize = (long) (productsVoList.size() / size + 1);

        // 상품이 없는 경우
        if (productsVoList.isEmpty()) {
            result.put("totalPageSize", 1);
            result.put("productList", null);
            return result;
        }

        for (ProductsVo product : productsVoList) {
            List<ColorDto> colors = new ArrayList<>();
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

                // 대표 이미지 url
                String imageUrl = productImagesMapper.getImageUrlsById(productColorsVo.getProductColorId())
                        .stream()
                        .findFirst()
                        .orElse(null);

                // 색상 정보 묶기
                ColorDto colorDto = ColorDto.builder()
                        .color(productColorsVo.getColor())
                        .sizesDtoList(sizes)
                        .imageUrl(imageUrl)
                        .productColorId(productColorsVo.getProductColorId())
                        .build();
                colors.add(colorDto);

                // 코디 상품 찾기
                List<CoordinatedProductDto> coordinatedProductList =  new ArrayList<>();
                List<Long> coordinateProductIds = new ArrayList<>();
                if (searchRange.equals("연관상품")) {
                    coordinateProductIds = productsMapper.getProductColorsIdsByNameOrDate(offset, size, orderBy, dateRange, searchRange, searchText);
                } else  {
                    coordinateProductIds = productsMapper.getCoordinatedProductIds(productColorsVo.getProductColorId());
                }
                if (!coordinateProductIds.isEmpty()) {
                    for (Long id : coordinateProductIds) {
                        ProductColorsVo color = productColorsMapper.getProductColorById(id);
                        // 상품의 공통 정보 불러오기
                        ProductsVo productsVo = productsMapper.getProductById(color.getProductId());
                        // 카테고리
                        String coordinatedCategory = getCategoryName(productsVo.getCategoryId());
                        // 대표 이미지 url
                        String coordinatedImageUrl = productImagesMapper.getImageUrlsById(color.getProductColorId())
                                .stream()
                                .findFirst()
                                .orElse(null);
                        // 사이즈 정보
                        List<SizesDto> coordinatedSizes = sizesMapper.getSizesByProductColorsId(color.getProductColorId())
                                .stream()
                                .map(SizesDto::toDto)
                                .toList();
                        CoordinatedProductDto coordinatedProductDto = CoordinatedProductDto.builder()
                                .category(coordinatedCategory)
                                .productName(productsVo.getProductName())
                                .color(color.getColor())
                                .sizesDtoList(coordinatedSizes)
                                .imageUrl(coordinatedImageUrl)
                                .registeredDate(productsVo.getRegisteredDate())
                                .build();

                        coordinatedProductList.add(coordinatedProductDto);
                    }
                }

                MainProductDto mainProductDto = MainProductDto.builder()
                        .category(category)
                        .productName(product.getProductName())
                        .colorList(colors)
                        .coordinatedProductDtoList(coordinatedProductList)
                        .build();

                productList.add(mainProductDto);
            }
        }

        result.put("totalPageSize", pageSize);
        result.put("productList", productList);
        return result;
    }

    public MainProductDto getCoordinatedProduct(Long productColorId) {
        ProductColorsVo productColor = productColorsMapper.getProductColorById(productColorId);
        ProductsVo product = productsMapper.getProductById(productColor.getProductId());
        List<ColorDto> colors = new ArrayList<>();
        MainProductDto mainProductDto = null;

        // 1. 부모 카테고리까지 찾아서 문자열로 변환
        String category = getCategoryName(product.getCategoryId());

        // 2. 색상 정보 찾기
        List<ProductColorsVo> productColorsVos = productColorsMapper.getProductColorsByProductId(product.getProductId());
        for (ProductColorsVo productColorsVo : productColorsVos) {
            // 3. 사이즈 정보 찾기
            List<SizesDto> sizes = sizesMapper.getSizesByProductColorsId(productColorsVo.getProductColorId())
                    .stream()
                    .map(SizesDto::toDto)
                    .toList();
            // 대표 이미지 url
            String imageUrl = productImagesMapper.getImageUrlsById(productColorsVo.getProductColorId())
                    .stream()
                    .findFirst()
                    .orElse(null);
            // 색상 정보 묶기
            ColorDto colorDto = ColorDto.builder()
                    .color(productColorsVo.getColor())
                    .sizesDtoList(sizes)
                    .imageUrl(imageUrl)
                    .productColorId(productColorsVo.getProductColorId())
                    .build();
            colors.add(colorDto);

                // 코디 상품 찾기
                List<CoordinatedProductDto> coordinatedProductList = new ArrayList<>();
                List<Long> coordinateProductIds = productsMapper.getCoordinatedProductIds(productColorsVo.getProductColorId());
                if (!coordinateProductIds.isEmpty()) {
                    for (Long id : coordinateProductIds) {
                        ProductColorsVo color = productColorsMapper.getProductColorById(id);
                        // 상품의 공통 정보 불러오기
                        ProductsVo productsVo = productsMapper.getProductById(color.getProductId());
                        // 카테고리
                        String coordinatedCategory = getCategoryName(productsVo.getCategoryId());
                        // 대표 이미지 url
                        String coordinatedImageUrl = productImagesMapper.getImageUrlsById(color.getProductColorId())
                                .stream()
                                .findFirst()
                                .orElse(null);
                        // 사이즈 정보
                        List<SizesDto> coordinatedSizes = sizesMapper.getSizesByProductColorsId(color.getProductColorId())
                                .stream()
                                .map(SizesDto::toDto)
                                .toList();
                        CoordinatedProductDto coordinatedProductDto = CoordinatedProductDto.builder()
                                .category(coordinatedCategory)
                                .productName(productsVo.getProductName())
                                .color(color.getColor())
                                .sizesDtoList(coordinatedSizes)
                                .imageUrl(coordinatedImageUrl)
                                .registeredDate(productsVo.getRegisteredDate())
                                .build();

                        coordinatedProductList.add(coordinatedProductDto);
                    }
                }

                mainProductDto = MainProductDto.builder()
                        .category(category)
                        .productName(product.getProductName())
                        .colorList(colors)
                        .coordinatedProductDtoList(coordinatedProductList)
                        .build();
            }
        return mainProductDto;
    }

    //상품명으로 검색해서 보내기 List<CoordinatedProductDto>
    public List<CoordinatedProductDto> searchCoordinatedProduct(String productName) {
        List<CoordinatedProductDto> productList = new ArrayList<>();
        List<ProductsVo> productsVoList = productsMapper.getProductByProductName(productName);

        for (ProductsVo productsVo : productsVoList) {
            List<ProductColorsVo> productColorsVos = productColorsMapper.getProductColorsByProductId(productsVo.getProductId());
            for (ProductColorsVo productColorsVo : productColorsVos) {
                String coordinatedCategory = getCategoryName(productsVo.getCategoryId());

                List<SizesDto> sizes = sizesMapper.getSizesByProductColorsId(productColorsVo.getProductColorId())
                        .stream()
                        .map(SizesDto::toDto)
                        .toList();
                String coordinatedImageUrl = productImagesMapper.getImageUrlsById(productColorsVo.getProductColorId())
                        .stream()
                        .findFirst()
                        .orElse(null);

                CoordinatedProductDto coordinatedProductDto = CoordinatedProductDto.builder()
                        .category(coordinatedCategory)
                        .productName(productsVo.getProductName())
                        .color(productColorsVo.getColor())
                        .sizesDtoList(sizes)
                        .imageUrl(coordinatedImageUrl)
                        .registeredDate((Date) productsVo.getRegisteredDate())
                        .build();
                productList.add(coordinatedProductDto);

            }
        }

        return productList;
    }

    // 코디 상품으로 추가
    public void createCoordinate(List<CoordinateDto> coordinateList) {
        // 존재하는 상품인지 검사 따로 해야하나요?
        for (CoordinateDto coordinateDto : coordinateList)
            productsMapper.createCoordinate(coordinateDto);
    }

    // 메인이랑 코디 상품 id 받아서 삭제 (여러 개 한 번에 삭제)
    public void deleteCoordinate(List<CoordinateDto> deleteList) {
        productsMapper.deleteCoordinates(deleteList);
    }
}


