package com.midnear.midnearshopping.domain.dto.productManagement;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {
    Long orderNumber;
    String orderStatus;
    String claimStatus;
    Date paymentDate;
    String productName;
    String color;
    String size;
    int quantity;
    BigDecimal totalPaymentAmount;
    BigDecimal allPayment;
    String orderName;
    String id;
    String recipientName;
    Date deliveryProcessDate;
    String orderContact;
    String postalCode;
    String address;
    String detailedAddress;
}
