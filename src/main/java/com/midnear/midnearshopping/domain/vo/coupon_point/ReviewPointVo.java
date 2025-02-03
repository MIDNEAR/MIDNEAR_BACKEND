package com.midnear.midnearshopping.domain.vo.coupon_point;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewPointVo {
    private int textReview;
    private int photoReview;
}
