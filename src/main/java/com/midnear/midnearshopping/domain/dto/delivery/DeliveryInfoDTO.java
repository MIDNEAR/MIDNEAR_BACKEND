package com.midnear.midnearshopping.domain.dto.delivery;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryInfoDTO {
    Long deliveryId;
    Long returnDeliveryId;
    String carrierCode;
    String invoiceNumber;
}
