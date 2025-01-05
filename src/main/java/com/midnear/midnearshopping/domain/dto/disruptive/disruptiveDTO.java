package com.midnear.midnearshopping.domain.dto.disruptive;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class disruptiveDTO {
    Long disruptiveCustomerId;
    String restrictionReason;
    String restrictionStatus;
    Long userId;
}
