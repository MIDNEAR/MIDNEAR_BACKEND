package com.midnear.midnearshopping.domain.dto.category;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCategoryDto {
    private String categoryName;
    private Long parentCategoryId;
}
