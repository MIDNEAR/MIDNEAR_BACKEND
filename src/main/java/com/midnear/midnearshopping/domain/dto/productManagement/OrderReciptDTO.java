package com.midnear.midnearshopping.domain.dto.productManagement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderReciptDTO {
    Long orderNumber;
    Date buyConfirmDate;
    String trackingDelivery;
    String productName;
    BigDecimal productPrice;
    int quantity;
    String recipientName;
    String recipientContact;
    String postalCode;
    String address;
    String detailedAddress;
    String deliveryRequest;
    String paymentMethod;
    BigDecimal deliveryCharge;
    BigDecimal totalOrderPayment;
    BigDecimal allPayment;
}
