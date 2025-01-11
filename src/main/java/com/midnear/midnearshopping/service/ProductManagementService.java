package com.midnear.midnearshopping.service;

import com.midnear.midnearshopping.domain.dto.category.CategoryDto;
import com.midnear.midnearshopping.domain.vo.category.CategoryVo;
import com.midnear.midnearshopping.mapper.Category.CategoriesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductManagementService {
    private final CategoriesMapper categoriesMapper;

    public List<CategoryDto> getCategories() {
        List<CategoryVo> categoryVoList = categoriesMapper.getCategories();

        //vo -> dto로 변환하여 Map에 저장 (부모 카테고리를 빠르게 찾으려고)
        Map<Long, CategoryDto> dtoMap = new HashMap<>();
        for (CategoryVo vo : categoryVoList) {
            dtoMap.put(vo.getCategoryId(), new CategoryDto(vo.getCategoryId(), vo.getCategoryName()));
        }

        // 카테고리 계층 만들기
        List<CategoryDto> rootCategories = new ArrayList<>(); // 최상위 카테고리 list
        for (CategoryVo vo : categoryVoList) {
            CategoryDto current = dtoMap.get(vo.getCategoryId());
            if (vo.getParentCategoryId() == null) {
                // 최상위 카테고리
                rootCategories.add(current);
            } else {
                // 하위 카테고리를 부모의 children 리스트에 추가
                CategoryDto parent = dtoMap.get(vo.getParentCategoryId());
                if (parent != null) {
                    parent.getChildren().add(current);
                }
            }
        }

        return rootCategories;
    }

}
