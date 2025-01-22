package com.midnear.midnearshopping.service.product;

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

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductsMapper productMapper;
    private final ProductColorsMapper productColorsMapper;
    private final SizesMapper sizesMapper;
    private static final int pageSize = 2;
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
        List<ProductDetailColorDto> productColorsDtoList = new ArrayList<>();

        for (ProductColorsVo productColorVo : productColorsVoList) {
            // ProductColorsDto 생성
            ProductDetailColorDto productColorsDto = new ProductDetailColorDto();
            productColorsDto.setProductColorId(productColorVo.getProductColorId());
            productColorsDto.setColor(productColorVo.getColor());
            productColorsDto.setProductId(productColorVo.getProductId());
            productColorsDto.setSaleStatus(productColorVo.getSaleStatus());

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

        // ProductsDto 생성
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
        //이미지 추가..
        List<String> images = productImagesMapper.getImageUrlsById(colorId);
        productsDto.setImages(images);

        return productsDto;
    }





}
