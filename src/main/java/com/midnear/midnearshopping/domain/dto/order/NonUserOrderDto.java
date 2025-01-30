package com.midnear.midnearshopping.domain.dto.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class NonUserOrderDto {
    Long orderId;
    String orderName;
    Integer deliveryAddrId;
    String orderContact;
    String orderEmail;
    BigDecimal allPayment;
    String recipientName;
    String postalCode;
    String address;
    String detailedAddress;
    String recipientContact;
    Integer userId;

    List<OrderProductsRequestDto> oderProductsRequestDtos;
}
