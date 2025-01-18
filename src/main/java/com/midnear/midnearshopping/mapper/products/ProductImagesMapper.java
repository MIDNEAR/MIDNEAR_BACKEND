package com.midnear.midnearshopping.mapper.products;

import com.midnear.midnearshopping.domain.vo.products.ProductImagesVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductImagesMapper {
    void uploadProductImage(ProductImagesVo productImagesVo);
}
