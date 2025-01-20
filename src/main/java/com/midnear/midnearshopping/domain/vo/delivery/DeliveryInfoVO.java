package com.midnear.midnearshopping.domain.vo.delivery;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryInfoVO {
    private int deliveryId;
    private String courier;
    private Integer invoiceNumber;
    private String trackingDelivery;
    private LocalDate deliveryProcessDate;
    private LocalDate deliveryCompleteDate;
    private String deliveryFee;
    private int orderProductId;
    private int deliveryAddressId;


}
