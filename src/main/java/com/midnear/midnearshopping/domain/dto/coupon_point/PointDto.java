package com.midnear.midnearshopping.domain.dto.coupon_point;

import com.midnear.midnearshopping.domain.vo.coupon_point.PointVo;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PointDto {
    private Long pointId;
    private Long amount;
    private String reason;
}
