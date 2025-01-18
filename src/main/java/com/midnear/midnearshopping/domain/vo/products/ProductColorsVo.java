package com.midnear.midnearshopping.domain.vo.products;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductColorsVo {
    private Long productColorId;
    //private Long coordinatedProductId;
    private String color;
    private Long productId;
    private boolean saleStatus;
}

