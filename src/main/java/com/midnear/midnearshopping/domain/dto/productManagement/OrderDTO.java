package com.midnear.midnearshopping.domain.dto.productManagement;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    Long orderNumber;
    String claimStatus;
    Date paymentDate;
    String productName;
    String color;
    String size;
    int quantity;
    BigDecimal allPayment;
    BigDecimal deliveryCharge;
    String orderName;
    String id;
    String recipientName;
    Date deliveryProcessDate;
    String trackingDelivery;
    String orderContact;
    String postalCode;
    String address;
    String detailedAddress;
}
