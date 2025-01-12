package com.midnear.midnearshopping.domain.vo.products;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SizesVo {
    private Long sizeId;
    private String size;
    private int stock;
    private Long productColorId;
}