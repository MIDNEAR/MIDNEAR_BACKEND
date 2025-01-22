package com.midnear.midnearshopping.domain.dto.disruptive;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
public class disruptiveListDTO {
    String id;
    String restrictionReason;
    Date createdAt;
    Long disruptiveCustomerId;
}
