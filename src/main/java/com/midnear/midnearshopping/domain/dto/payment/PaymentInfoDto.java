package com.midnear.midnearshopping.domain.dto.payment;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

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
    Date paymentDate;
    BigDecimal deliveryCharge;
}
