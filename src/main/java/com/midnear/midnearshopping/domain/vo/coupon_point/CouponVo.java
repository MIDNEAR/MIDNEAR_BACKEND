package com.midnear.midnearshopping.domain.vo.coupon_point;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponVo {
    private Long couponId;
    private String couponName;
    private int discountRate;
}
