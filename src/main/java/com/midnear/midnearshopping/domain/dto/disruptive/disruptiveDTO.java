package com.midnear.midnearshopping.domain.dto.disruptive;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class disruptiveDTO {
    String id;
    String restrictionReason;
}
