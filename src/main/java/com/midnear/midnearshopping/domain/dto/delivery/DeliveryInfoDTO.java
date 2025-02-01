package com.midnear.midnearshopping.domain.dto.delivery;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryInfoDTO {
    Long deliveryId;
    String carrierCode;
    String invoiceNumber;
}
