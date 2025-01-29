package com.midnear.midnearshopping.mapper.products;

import com.midnear.midnearshopping.domain.dto.products.ProductsListDto;
import com.midnear.midnearshopping.domain.vo.products.ProductsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductsMapper {
    void registerProducts(ProductsVo productsVo);
    ProductsVo getProductById(Long productId);
    void updateProduct(ProductsVo productsVo);
    void deleteProducts(List<Long> deleteList);
    List<ProductsVo> getProductPaging(@Param("offset")int offset, @Param("size")int size, @Param("orderBy")String orderBy, @Param("dateRange")String dateRange, @Param("searchRange")String searchRange, @Param("searchText")String searchText);
    List<ProductsVo> getProductsBySaleStatus(@Param("offset")int offset, @Param("size")int size, @Param("orderBy")String orderBy, @Param("dateRange")String dateRange, @Param("searchRange")String searchRange, @Param("searchText")String searchText);
    List<ProductsListDto> getProductsByCategoryWithHierarchy(@Param("categoryId")Long categoryId, @Param("sort") String sort,@Param("offset") int offset, @Param("pageSize") int pageSize);
    ProductsVo findByProductId(Long colorId);
    List<ProductsVo> getProductsByCategoryIds(List<Long> categories);
    Long count(@Param("dateRange")String dateRange, @Param("searchRange")String searchRange, @Param("searchText")String searchText);
}
