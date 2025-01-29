package com.midnear.midnearshopping.domain.dto.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
@Getter
@Setter
public class UserOrderProductsDto {
    private Long orderProductId;
    private String size; //받아야됨
    private int quantity;// 받아야됨
    private BigDecimal couponDiscount; //받아야됨
    private Date buyConfirmDate;
    private String claimStatus;
    private BigDecimal pointDiscount; //받아야됨
    private Long deliveryId; //배송정보 id
    private Long orderId; // 안받아도됨
    private BigDecimal price; // 받아야됨
    private String productName; //받아야됨
    private String color; // 받아야됨
    private BigDecimal deliveryCharge; // 받아야됨
    private String productMainImage; // 안받아도죔
}
