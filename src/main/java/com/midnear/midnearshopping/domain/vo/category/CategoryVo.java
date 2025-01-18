package com.midnear.midnearshopping.domain.vo.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryVo {
    private Long categoryId;
    private String categoryName;
    private Long parentCategoryId;

}
