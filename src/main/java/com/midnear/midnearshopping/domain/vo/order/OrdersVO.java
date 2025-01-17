package com.midnear.midnearshopping.domain.vo.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdersVO {
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
}
