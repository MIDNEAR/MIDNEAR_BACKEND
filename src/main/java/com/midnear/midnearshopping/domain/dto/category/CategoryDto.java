package com.midnear.midnearshopping.domain.dto.category;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {
    private Long categoryId;
    private String categoryName;
    private List<CategoryDto> children = new ArrayList<>();

    public CategoryDto(Long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
}
