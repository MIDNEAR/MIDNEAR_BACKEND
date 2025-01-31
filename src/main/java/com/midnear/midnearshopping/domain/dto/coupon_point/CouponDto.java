package com.midnear.midnearshopping.domain.dto.coupon_point;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponDto {
    private String couponName;
    private int discountRate;
}
