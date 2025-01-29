package com.midnear.midnearshopping.domain.dto.order;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOrderProductCheckDto {
    private Long orderProductId;
    private String size;
    private int quantity;
    private String claimStatus;
    private BigDecimal pointDiscount;
    private BigDecimal payPrice; // 최종 구매가
    private String productName;
    private String productMainImage;
}
