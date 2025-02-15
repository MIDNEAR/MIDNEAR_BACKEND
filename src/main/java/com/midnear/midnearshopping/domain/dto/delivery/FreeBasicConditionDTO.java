package com.midnear.midnearshopping.domain.dto.delivery;

import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FreeBasicConditionDTO{
   private BigDecimal basicDeliveryCost;
   private BigDecimal freeDeliveryCondition;
}
