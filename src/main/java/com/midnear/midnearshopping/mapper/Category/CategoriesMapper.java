package com.midnear.midnearshopping.mapper.Category;

import com.midnear.midnearshopping.domain.vo.category.CategoryVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoriesMapper {
    List<CategoryVo> getCategories();
    CategoryVo getCategoryById(Long categoryId);
    List<Long> getCategoryIdByCategoryName(String searchText);
}
