package com.midnear.midnearshopping.domain.dto.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NonUserOrderInfoRequest {
    String orderName;
    String orderContact;
    String orderNumber;
}
