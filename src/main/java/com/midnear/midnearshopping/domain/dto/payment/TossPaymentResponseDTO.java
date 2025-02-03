package com.midnear.midnearshopping.domain.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TossPaymentResponseDTO {
    private String paymentKey;
    private String orderId;
    private BigDecimal amount;
    private String status;

}