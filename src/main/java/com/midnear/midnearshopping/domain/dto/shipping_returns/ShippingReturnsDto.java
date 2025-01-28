package com.midnear.midnearshopping.domain.dto.shipping_returns;

import com.midnear.midnearshopping.domain.vo.shipping_returns.ShippingReturnsVo;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShippingReturnsDto {
    private Long id;
    private String shippingInfo;
    private String shippingNotice;
    private String shippingReturnsPolicy;

    public static ShippingReturnsDto toDto(ShippingReturnsVo shippingReturnsVo) {
        return ShippingReturnsDto.builder()
                .id(shippingReturnsVo.getId())
                .shippingInfo(shippingReturnsVo.getShippingInfo())
                .shippingNotice(shippingReturnsVo.getShippingNotice())
                .shippingReturnsPolicy(shippingReturnsVo.getShippingReturnsPolicy())
                .build();
    }
}
