package com.midnear.midnearshopping.domain.dto.disruptive;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class disruptiveListDTO {
    String id;
    String restrictionReason;
    Date createdAt;
}
