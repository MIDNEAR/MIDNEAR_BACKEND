package com.midnear.midnearshopping.domain.dto.order;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOrderCheckDto {
    Long orderId;
    String orderNumber;
    Date orderDate;
    List<UserOrderProductCheckDto> userOrderProductCheckDtos;
}
