package com.midnear.midnearshopping.mapper.products;

import com.midnear.midnearshopping.domain.vo.products.ProductsVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductsMapper {
    void registerProducts(ProductsVo productsVo);

    List<ProductsVo> findAll();

    void deleteProducts(List<Long> productList);
}
