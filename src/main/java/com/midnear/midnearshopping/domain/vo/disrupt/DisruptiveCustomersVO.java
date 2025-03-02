package com.midnear.midnearshopping.domain.vo.disrupt;

import lombok.*;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DisruptiveCustomersVO {
    Long disruptiveCustomerId;
    String restrictionReason;
    String restrictionStatus;
    Long userId;
    Date created_at;
}
