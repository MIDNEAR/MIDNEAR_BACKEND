package com.midnear.midnearshopping.domain.dto.coupon_point;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PointToSelectedUserDto {
    private Long amount;
    private String reason;
    private List<String> userIdList;
}
