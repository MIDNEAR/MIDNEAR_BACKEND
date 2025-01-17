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
   Long orderProductId;
   String size;
   int quantity;
   String orderStatus;
   BigDecimal couponDiscount;
   String claimStatus;
   Date buyConfirmDate;
   BigDecimal pointJDiscount;
   Long productId;
   Long orderId;
}
