package com.midnear.midnearshopping.domain.dto.order;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderProductDto {
    private Long orderProductId;
    private String size;
    private int quantity;
    private String claimStatus;
    private BigDecimal pointDiscount;
    private BigDecimal productPrice; // 상품가격
    private String productName;
    private String productMainImage;
}
