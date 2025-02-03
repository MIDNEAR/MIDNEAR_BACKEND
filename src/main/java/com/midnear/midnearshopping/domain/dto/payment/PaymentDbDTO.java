package com.midnear.midnearshopping.domain.dto.payment;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDbDTO {
    String tossPaymentMethod;
    BigDecimal totalOrderPayment;
    String paymentStatus;
    String tossOrderId;
    String tossPaymentKey;
}
