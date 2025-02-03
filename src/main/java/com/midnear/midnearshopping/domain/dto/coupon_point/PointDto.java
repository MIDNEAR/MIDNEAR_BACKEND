package com.midnear.midnearshopping.domain.dto.coupon_point;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PointDto {
    private Long amount;
    private String reason;
    private Long reviewId;
    private Long userId;
}
