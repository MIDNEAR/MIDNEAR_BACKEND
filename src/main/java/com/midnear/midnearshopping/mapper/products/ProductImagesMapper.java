package com.midnear.midnearshopping.mapper.products;

import com.midnear.midnearshopping.domain.vo.products.ProductImagesVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductImagesMapper {
    void uploadProductImage(ProductImagesVo productImagesVo);

    List<ProductImagesVo> getImagesById(Long id);

    void deleteProductImages(Long productColorId);

    List<String> getImageUrlsById(Long colorId);
    String getMainImageUrlsById(Long colorId);
}
