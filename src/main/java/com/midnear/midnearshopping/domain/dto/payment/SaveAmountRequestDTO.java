package com.midnear.midnearshopping.domain.dto.payment;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SaveAmountRequestDTO {
    private String orderId;
    private String tossOrderId;
    private String paymentKey;
    private BigDecimal amount;
}
