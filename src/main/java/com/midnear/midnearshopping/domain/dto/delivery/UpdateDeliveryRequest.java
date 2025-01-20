package com.midnear.midnearshopping.domain.dto.delivery;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDeliveryRequest {
    private Integer deliveryAddressId;
    private String deliveryRequest; //배송 요청사할
}
