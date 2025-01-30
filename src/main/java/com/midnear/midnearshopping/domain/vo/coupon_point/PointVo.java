package com.midnear.midnearshopping.domain.vo.coupon_point;

import com.midnear.midnearshopping.domain.dto.coupon_point.PointDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PointVo {
    private Long pointId;
    private Long amount;
    private String reason;

    public static PointVo toEntity(PointDto pointDto) {
        return PointVo.builder()
                .pointId(null)
                .amount(pointDto.getAmount())
                .reason(pointDto.getReason())
                .build();
    }
}
