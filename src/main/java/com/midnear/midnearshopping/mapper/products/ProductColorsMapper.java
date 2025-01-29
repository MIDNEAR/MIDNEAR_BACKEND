package com.midnear.midnearshopping.mapper.products;

import com.midnear.midnearshopping.domain.vo.products.ProductColorsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductColorsMapper {
    void registerProducts(ProductColorsVo productColorsVo);
    List<ProductColorsVo> getProductColorsByProductId(Long productId);
    List<ProductColorsVo> searchingProductColorsByProductId(@Param("productId")Long productId, @Param("searchText")String searchText, @Param("searchRange")String searchRange);
    void deleteColors(List<Long> productList);
    void setOnSale(List<Long> productList);
    void setSoldOut(List<Long> productList);
    void setDiscontinued(List<Long> productList);
    Long getProductIdByColor(Long colorId);
    String getColorById(Long colorId);
    void updateProductColor(ProductColorsVo productColorsVo);
}
