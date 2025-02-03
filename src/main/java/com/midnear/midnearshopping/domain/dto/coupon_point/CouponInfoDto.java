package com.midnear.midnearshopping.domain.dto.coupon_point;

import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class CouponInfoDto {
    int userCouponId;
    boolean usedStatus;
    String couponName;
    int discountRate;

}
