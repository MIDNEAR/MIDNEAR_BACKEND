package com.midnear.midnearshopping.mapper.products;

import com.midnear.midnearshopping.domain.vo.products.ProductColorsVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductColorsMapper {
    void registerProducts(ProductColorsVo productColorsVo);
    List<ProductColorsVo> getProductColorsByProductId(Long productId);
}
