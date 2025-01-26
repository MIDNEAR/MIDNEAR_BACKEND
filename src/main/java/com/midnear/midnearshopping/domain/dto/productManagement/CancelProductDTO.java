package com.midnear.midnearshopping.domain.dto.productManagement;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CancelProductDTO {
    Long orderNumber;
    String trackingDelivery;
    String cancellationStatus;
    Date paymentDate;
    Date cancellationRequestDate;
    String cancelReason;
    Date cancellationApprovalDate;
    String productName;
    String color;
    String size;
    int quantity;
    BigDecimal productPrice;
    BigDecimal couponDiscount;
    BigDecimal pointUsage;
    BigDecimal totalPaymentAmount;
    BigDecimal deliveryCharge;
    BigDecimal allPayment;
    String order_name;
    String id;
    String recipientName;
    String orderContact;
    String postalCode;
    String address;
    String detailedAddress;
}
