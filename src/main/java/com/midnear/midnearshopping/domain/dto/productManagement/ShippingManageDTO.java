package com.midnear.midnearshopping.domain.dto.productManagement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShippingManageDTO {
    BigDecimal basicDeliveryCost;
    BigDecimal freeDeliveryCondition;
    BigDecimal jejuDeliveryCost;
    BigDecimal islandMountainDeliveryCost;
}
