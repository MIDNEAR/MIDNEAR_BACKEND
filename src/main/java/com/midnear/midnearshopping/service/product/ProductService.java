package com.midnear.midnearshopping.service.product;

import com.midnear.midnearshopping.domain.dto.coordinate.CoordinateDto;
import com.midnear.midnearshopping.domain.dto.products.*;
import com.midnear.midnearshopping.domain.vo.category.CategoryVo;
import com.midnear.midnearshopping.domain.vo.products.ProductColorsVo;
import com.midnear.midnearshopping.domain.vo.products.ProductsVo;
import com.midnear.midnearshopping.domain.vo.products.SizesVo;
import com.midnear.midnearshopping.mapper.Category.CategoriesMapper;
import com.midnear.midnearshopping.mapper.products.ProductColorsMapper;
import com.midnear.midnearshopping.mapper.products.ProductImagesMapper;
import com.midnear.midnearshopping.mapper.products.ProductsMapper;
import com.midnear.midnearshopping.mapper.products.SizesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductsMapper productMapper;
    private final ProductColorsMapper productColorsMapper;
    private final SizesMapper sizesMapper;
    private static final int pageSize = 4;
    private final ProductsMapper productsMapper;
    private final ProductImagesMapper productImagesMapper;

    public List<ProductsListDto> getProductsByCategoryWithHierarchy(Long categoryId, int pageNumber, String sort) {
        int offset = (pageNumber - 1) * pageSize; // 민정님 페이지 갖다쓰기 개꿀
        return productMapper.getProductsByCategoryWithHierarchy(categoryId, sort, offset, pageSize);
    }

    public ProductsDetailDto getProductDetails(Long colorId) {
        Long productId = productColorsMapper.getProductIdByColor(colorId);
        // ProductColors 조회
        List<ProductColorsVo> productColorsVoList = productColorsMapper.getProductColorsByProductId(productId);
        String currentColor = productColorsMapper.getColorById(colorId);
        List<ProductDetailColorDto> productColorsDtoList = new ArrayList<>();

        //상품을 조회 == 특정 색상의 상품에 대한 이미지, 그 색상별 사이즈,사이즈별 수량, 상품의 다른 색상 및 색상들의 판매 상태
        //+ 상품 데베에 등록된 각종 상품 정보들.. 을 알아야 하는거라 굉장히 복잡합니다
        //컬러 디티오가 조회된 상품의 다른 색상에 대한 이미지, 그 색상들의 사이즈 및 사이즈 별 수량을 담고있습니다
        for (ProductColorsVo productColorVo : productColorsVoList) {
            ProductDetailColorDto productColorsDto = new ProductDetailColorDto();
            productColorsDto.setProductColorId(productColorVo.getProductColorId());
            productColorsDto.setColor(productColorVo.getColor());
            productColorsDto.setProductId(productColorVo.getProductId());
            productColorsDto.setSaleStatus(productColorVo.getSaleStatus());
            //여기에 이미지 가져오는거 추가!!!
            String mainImage = productImagesMapper.getMainImageUrlsById(productColorVo.getProductColorId());
            productColorsDto.setMainImage(mainImage);

            // Sizes 조회 및 매핑
            List<SizesVo> sizesVoList = sizesMapper.getSizesByProductColorsId(productColorVo.getProductColorId());
            List<ProductDetailSizeDto> sizesDtoList = new ArrayList<>();
            for (SizesVo sizesVo : sizesVoList) {
                ProductDetailSizeDto sizesDto = new ProductDetailSizeDto();
                sizesDto.setSize(sizesVo.getSize());
                sizesDto.setStock(sizesVo.getStock());
                sizesDtoList.add(sizesDto);
            }
            productColorsDto.setSizes(sizesDtoList);
            productColorsDtoList.add(productColorsDto);
        }

        ProductsVo productsVo = productsMapper.findByProductId(productId);

        // ProductsDto 생성 --> 요기가 상품에대한 기본 정보들 조회
        ProductsDetailDto productsDto = new ProductsDetailDto();
        productsDto.setProductId(productId); // ID는 컨트롤러에서 전달받은 값
        productsDto.setProductName(productsVo.getProductName());
        productsDto.setDetail(productsVo.getDetail());
        productsDto.setPrice(productsVo.getPrice());
        productsDto.setCategoryId(productsVo.getCategoryId());
        productsDto.setDiscountPrice(productsVo.getDiscountPrice());
        productsDto.setDiscountRate(productsVo.getDiscountRate());
        productsDto.setDiscountStartDate(productsVo.getDiscountStartDate());
        productsDto.setDiscountEndDate(productsVo.getDiscountEndDate());
        productsDto.setSizeGuide(productsVo.getSizeGuide());
        productsDto.setRegisteredDate(productsVo.getRegisteredDate());
        productsDto.setColors(productColorsDtoList);
        //현재 색상이 뭔지 알려줘야할거같음
        productsDto.setCurrentColor(currentColor);
        //이미지 추가..
        List<String> images = productImagesMapper.getImageUrlsById(colorId);
        productsDto.setImages(images);

        return productsDto;
    }

    public List<CoordinateProductDto> getCoordinateProducts(Long productColorId){
        return productMapper.getCoordinateProducts(productColorId);
    }

    public ProductListInfoDto getProductListInfo() {
        ProductListInfoDto dto = productMapper.getTotalAndPage();
        dto.setPage(pageSize);
        return dto;
    }
    public ProductListInfoDto getCategoryProductListInfo(Long categoryId) {
        return Optional.ofNullable(productMapper.getCategoryTotalAndPage(categoryId))
                .map(dto -> {
                    dto.setPage(pageSize); // null이 아닐 때도 pageSize 설정
                    return dto;
                })
                .orElseGet(() -> {
                    ProductListInfoDto dto = new ProductListInfoDto();
                    dto.setTotal(0);
                    dto.setPage(pageSize);
                    return dto;
                });

}





}
