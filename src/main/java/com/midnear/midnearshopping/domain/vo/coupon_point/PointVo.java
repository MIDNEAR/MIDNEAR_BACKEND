package com.midnear.midnearshopping.domain.vo.coupon_point;

import com.midnear.midnearshopping.domain.dto.coupon_point.PointDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PointVo {
    private Long pointId;
    private Long amount;
    private String reason;
    private Long reviewId;
    private Long userId;
    private Date grantDate;
}
