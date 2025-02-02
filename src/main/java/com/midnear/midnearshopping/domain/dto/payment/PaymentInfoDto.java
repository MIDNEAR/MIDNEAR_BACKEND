package com.midnear.midnearshopping.domain.dto.payment;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Builder
@Getter

public class PaymentInfoDto {
    String tossPaymentMethod;
    BigDecimal totalOrderPayment;
    String paymentStatus;
    String tossOrderId;
    String tossPaymentKey;
    BigDecimal allPayment;
    BigDecimal deliveryCharge;
}
