package com.midnear.midnearshopping.service;

import com.midnear.midnearshopping.domain.dto.FileDto;
import com.midnear.midnearshopping.domain.dto.category.CategoryDto;
import com.midnear.midnearshopping.domain.dto.products.*;
import com.midnear.midnearshopping.domain.vo.category.CategoryVo;
import com.midnear.midnearshopping.domain.vo.notice.NoticeImagesVo;
import com.midnear.midnearshopping.domain.vo.products.ProductColorsVo;
import com.midnear.midnearshopping.domain.vo.products.ProductImagesVo;
import com.midnear.midnearshopping.domain.vo.products.ProductsVo;
import com.midnear.midnearshopping.domain.vo.products.SizesVo;
import com.midnear.midnearshopping.mapper.Category.CategoriesMapper;
import com.midnear.midnearshopping.mapper.products.ProductColorsMapper;
import com.midnear.midnearshopping.mapper.products.ProductImagesMapper;
import com.midnear.midnearshopping.mapper.products.ProductsMapper;
import com.midnear.midnearshopping.mapper.products.SizesMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductManagementService {
    private final CategoriesMapper categoriesMapper;
    private final ProductsMapper productsMapper;
    private final ProductColorsMapper productColorsMapper;
    private final SizesMapper sizesMapper;
    private final ProductImagesMapper productImagesMapper;
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
            //color.setColor(color.getColor()); //이게 뭐냐/.....ㅋㅋ
            // 색상 등록 하고 productColorId 반환
            ProductColorsVo productColorsVo = ProductColorsVo.builder()
                    .color(color.getColor())
                    .productId(productsVo.getProductId())
                    .build();
            productColorsMapper.registerProducts(productColorsVo);
            // 색상별 사이즈 등록
            List<SizesDto> sizes = color.getSizes();
            for (SizesDto size : sizes) {
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

    public List<ProductManagementListDto> getProductList() {
        List<ProductManagementListDto> productList = new ArrayList<>();


        // 모든 상품 불러오기
        List<ProductsVo> productsVoList = productsMapper.findAll();
        for (ProductsVo product : productsVoList) {
            List<ProductColorsListDto> colors = new ArrayList<>();
            // 1. 부모 카테고리까지 찾아서 문자열로 변환
            String category = getCategoryName(product.getCategoryId());

            // 2. 색상 정보 찾기
            List<ProductColorsVo> productColorsVos = productColorsMapper.getProductColorsByProductId(product.getProductId());
            for (ProductColorsVo productColorsVo : productColorsVos) {
                // 3. 사이즈 정보 찾기
                List<SizesVo> sizes = sizesMapper.getSizesByProductColorsId(productColorsVo.getProductColorId());
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

}
