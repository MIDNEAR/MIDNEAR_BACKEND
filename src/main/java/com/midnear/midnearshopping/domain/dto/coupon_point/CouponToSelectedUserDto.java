package com.midnear.midnearshopping.domain.dto.coupon_point;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponToSelectedUserDto {
    private String couponName;
    private int discountRate;
    private List<String> userIdList;

}
