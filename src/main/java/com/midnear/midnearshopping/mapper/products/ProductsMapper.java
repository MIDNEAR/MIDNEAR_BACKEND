package com.midnear.midnearshopping.mapper.products;

import com.midnear.midnearshopping.domain.dto.products.ProductsDto;
import com.midnear.midnearshopping.domain.dto.products.ProductsListDto;
import com.midnear.midnearshopping.domain.vo.products.ProductsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductsMapper {
    void registerProducts(ProductsVo productsVo);

    List<ProductsVo> findAll();

    void deleteProducts(List<Long> productList);

    List<ProductsListDto> getProductsByCategoryWithHierarchy(@Param("categoryId")Long categoryId, @Param("sort") String sort,@Param("offset") int offset, @Param("pageSize") int pageSize);
    ProductsVo findByProductId(Long colorId);
}
