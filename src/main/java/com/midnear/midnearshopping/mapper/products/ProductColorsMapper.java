package com.midnear.midnearshopping.mapper.products;

import com.midnear.midnearshopping.domain.vo.products.ProductColorsVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductColorsMapper {
    void registerProducts(ProductColorsVo productColorsVo);
    List<ProductColorsVo> getProductColorsByProductId(Long productId);
    void setOnSale(List<Long> productList);
    void setSoldOut(List<Long> productList);
    void setDiscontinued(List<Long> productList);
    void deleteColors(List<Long> productList);
    boolean existsByProductColorId(Long productColorId);
    void updateProductColor(ProductColorsVo productColorsVo);
}
