package com.midnear.midnearshopping.domain.vo.shipping_returns;

import com.midnear.midnearshopping.domain.dto.shipping_returns.ShippingReturnsDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShippingReturnsVo {
    private Long id;
    private String shippingInfo;
    private String shippingNotice;
    private String shippingReturnsPolicy;

    public static ShippingReturnsVo toEntity(ShippingReturnsDto shippingReturnsDto) {
        return ShippingReturnsVo.builder()
                .id(shippingReturnsDto.getId())
                .shippingInfo(shippingReturnsDto.getShippingInfo())
                .shippingNotice(shippingReturnsDto.getShippingNotice())
                .shippingReturnsPolicy(shippingReturnsDto.getShippingReturnsPolicy())
                .build();
    }
}
