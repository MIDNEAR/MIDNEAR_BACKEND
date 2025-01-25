package com.midnear.midnearshopping.domain.dto.products;

import com.midnear.midnearshopping.domain.enums.ProductStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ProductColorsListDto {
    private String color;
    private List<SizesDto> sizes;
    private ProductStatus saleStatus;
}
