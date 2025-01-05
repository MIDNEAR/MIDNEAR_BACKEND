package com.midnear.midnearshopping.domain.vo.disrupt;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class disruptiveCustomersVO {
    Long disruptiveCustomerId;
    String restrictionReason;
    String restrictionStatus;
    Long userId;
}
