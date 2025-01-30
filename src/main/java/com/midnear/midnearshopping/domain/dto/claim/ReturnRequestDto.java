package com.midnear.midnearshopping.domain.dto.claim;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ReturnRequestDto {

    String pickupMethod;
    String returnReason;
    Long OrderProductId;

}
