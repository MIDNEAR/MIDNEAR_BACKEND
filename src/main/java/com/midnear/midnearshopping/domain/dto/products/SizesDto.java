package com.midnear.midnearshopping.domain.dto.products;

import com.midnear.midnearshopping.domain.vo.products.SizesVo;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SizesDto {
    private Long sizeId;
    private String size;
    private int stock;
    private Long productColorId;

    public static SizesDto toDto(SizesVo sizesVo) {
        return SizesDto.builder()
                .sizeId(sizesVo.getSizeId())
                .size(sizesVo.getSize())
                .stock(sizesVo.getStock())
                .productColorId(sizesVo.getProductColorId())
                .build();
    }

}
