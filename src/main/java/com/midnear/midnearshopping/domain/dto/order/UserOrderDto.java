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
    String orderContact;
    String orderEmail;
    int totalOrderQuantity;
    BigDecimal totalPaymentAmount;
    Date orderDate;
    String recipientName;
    String postalCode;
    String address;
    String detailedAddress;
    BigDecimal pointUsage;
    Long orderNumber;
    Long userId;
    Long deliveryId;
    BigDecimal allPayment;
    List<UserOrderProductsDto> userOrderProductsDto;
}
