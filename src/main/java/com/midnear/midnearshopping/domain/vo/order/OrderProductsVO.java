package com.midnear.midnearshopping.domain.vo.order;

import jakarta.validation.constraints.DecimalMax;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderProductsVO {
   private Long orderProductId;
   private String size;
   private int quantity;
   private BigDecimal couponDiscount;
   private Date buyConfirmDate;
   private String claimStatus;
   private BigDecimal pointDiscount;
   private Long deliveryId; //배송정보 id
   private Long orderId;
   private BigDecimal productPrice;
   private String productName;
   private String color;
   private BigDecimal deliveryCharge;
   private String productMainImage;

}
