package com.midnear.midnearshopping.domain.vo.products;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductColorsVo {
    private Long productColorsId;
    private Long coordinatedProductId;
    private String color;
    private Long productId;
}

