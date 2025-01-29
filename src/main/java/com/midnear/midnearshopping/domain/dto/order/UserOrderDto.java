package com.midnear.midnearshopping.domain.dto.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UserOrderDto {
    Long orderId;
    String orderName;
    Integer deliveryAddrId;
    String orderContact;
    String orderEmail;
    Long userId;
    BigDecimal allPayment;
    List<OrderProductsRequestDto> oderProductsRequestDtos;
}
