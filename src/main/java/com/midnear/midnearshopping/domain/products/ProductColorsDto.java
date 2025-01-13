package com.midnear.midnearshopping.domain.products;

import lombok.Getter;

import java.util.List;

@Getter
public class ProductColorsDto {
    private Long productColorsId;
    private Long coordinatedProductId;
    private String color;
    private  Long productId;
    private List<SizesDto> sizes;
}
