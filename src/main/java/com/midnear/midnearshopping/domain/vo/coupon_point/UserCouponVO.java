package com.midnear.midnearshopping.domain.vo.coupon_point;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCouponVO {
    Long userCouponId;
    boolean usedStatus;
    Long userId;
}
