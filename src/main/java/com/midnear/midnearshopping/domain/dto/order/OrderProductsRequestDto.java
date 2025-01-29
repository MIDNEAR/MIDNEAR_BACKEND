package com.midnear.midnearshopping.domain.dto.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class OrderProductsRequestDto {
    private Long productColorId;
    private String size; //받아야됨
    private int quantity;// 받아야됨
    private BigDecimal couponDiscount; //받아야됨
    private BigDecimal pointDiscount; //받아야됨
    private BigDecimal productPrice; // 받아야됨
    private BigDecimal deliveryCharge; // 받아야됨
}
