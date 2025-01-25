package com.midnear.midnearshopping.mapper.products;

import com.midnear.midnearshopping.domain.vo.products.ProductsVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductsMapper {
    void registerProducts(ProductsVo productsVo);

    ProductsVo getProductById(Long productId);

    void updateProduct(ProductsVo productsVo);

    void deleteProducts(List<Long> deleteList);

    List<ProductsVo> getProductPaging(int offset, int size, String orderBy, String dateRange);
}
