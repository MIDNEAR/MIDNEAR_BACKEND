package com.midnear.midnearshopping.domain.vo.products;

import com.midnear.midnearshopping.domain.dto.products.SizesDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SizesVo {
    private Long sizeId;
    private String size;
    private int stock;
    private Long productColorId;

    public static SizesVo toEntity(SizesDto sizesDto) {
        return SizesVo.builder()
                .sizeId(sizesDto.getSizeId())
                .size(sizesDto.getSize())
                .stock(sizesDto.getStock())
                .productColorId(sizesDto.getProductColorId())
                .build();
    }
}