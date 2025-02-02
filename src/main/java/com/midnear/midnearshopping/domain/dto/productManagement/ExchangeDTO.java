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
public class ExchangeDTO {
    Long orderNumber;
    String trackingDelivery;
    String exchangeStatus;
    String pickupStatus;
    Date paymentDate;
    Date exchangeRequestDate;
    String exchangeReason;
    Date pickupCompleteDate;
    BigDecimal exchangeDeliveryFee;
    String exchangePaymentStatus;
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
    String pickupCourier;
    Long pickupInvoice;
    String resendCourier;
    Long resendInvoiceNumber;
    Date redeliveryDate;
    String orderContact;
    String postalCode;
    String address;
    String detailedAddress;
}
