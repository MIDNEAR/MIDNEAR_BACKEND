package com.midnear.midnearshopping.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentErrorResponse {
    private int code;
    private String message;
}