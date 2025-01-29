package com.midnear.midnearshopping.domain.vo.order;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class OrdersVO {
    Long orderId;
    String orderName;
    String orderContact;
    String orderEmail;
    Date orderDate;
    String recipientName;
    String postalCode;
    String address;
    String detailedAddress;
    String orderNumber;
    Integer userId;
    BigDecimal allPayment;
}
