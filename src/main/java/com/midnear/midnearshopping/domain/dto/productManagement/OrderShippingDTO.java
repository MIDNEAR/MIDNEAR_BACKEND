package com.midnear.midnearshopping.domain.dto.productManagement;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderShippingDTO {
    Long orderNumber;
    String trackingDelivery;
    Date paymentDate;
    String productName;
    String color;
    String size;
    int quantity;
    BigDecimal productPrice;
    BigDecimal couponDiscount;
    BigDecimal pointDiscount;
    BigDecimal allPayment;
    BigDecimal deliveryCharge;
    String orderName;
    String id;
    String recipientName;
    String courier;
    Long invoiceNumber;
    Date deliveryProcess_date;
    String orderContact;
    String postalCode;
    String address;
    String detailedAddress;
}
