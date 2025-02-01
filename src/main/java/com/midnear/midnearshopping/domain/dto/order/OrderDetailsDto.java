package com.midnear.midnearshopping.domain.dto.order;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailsDto {
    Long orderId;
    String orderNumber;
    Date orderDate;
    String recipientName;
    String postalCode; //필요했군.
    String recipientContact;
    String address;
    String detailedAddress;
    List<UserOrderProductCheckDto> userOrderProductCheckDtos;
    //결제정보 보여주는건 여기에 추가
}
