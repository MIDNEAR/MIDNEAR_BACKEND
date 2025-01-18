package com.midnear.midnearshopping.domain.products;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SizesDto {
    private Long sizeId;
    private String size;
    private int stock;
    private Long productColorId;
}
