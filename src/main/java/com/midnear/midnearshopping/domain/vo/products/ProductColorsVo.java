package com.midnear.midnearshopping.domain.vo.products;

import com.midnear.midnearshopping.domain.dto.products.ProductColorsDto;
import com.midnear.midnearshopping.domain.enums.ProductStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductColorsVo {
    private Long productColorId;
    //private Long coordinatedProductId;
    private String color;
    private Long productId;
    private ProductStatus saleStatus;

    public static ProductColorsVo toEntity(final ProductColorsDto productColorsDto) {
        return ProductColorsVo.builder()
                .productColorId(productColorsDto.getProductColorId())
                .color(productColorsDto.getColor())
                .productId(productColorsDto.getProductId())
                .saleStatus(productColorsDto.getSaleStatus())
                .build();
    }
}

