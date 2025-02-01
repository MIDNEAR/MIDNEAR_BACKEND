package com.midnear.midnearshopping.domain.dto.productManagement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReturnDTO {
    Long returnId;
    Long orderNumber;
    String trackingDelivery;
    String returnStatus;
    String returnDeliveryStatus;
    Date paymentDate;
    Date returnRequestDate;
    String returnReason;
    Date collectionCompleteDate;
    BigDecimal deliveryFee;
    String paymentStatus;
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
    String invoiceNumber;
    String orderContact;
    String recipientContact;
}
