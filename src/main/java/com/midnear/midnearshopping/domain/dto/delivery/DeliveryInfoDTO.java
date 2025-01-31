package com.midnear.midnearshopping.domain.dto.delivery;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryInfoDTO {
    Long pickupDeliveryId;
    Long deliveryId;
    Long returnDeliveryId;
    String pickupInvoice;
    String carrierCode;
    String invoiceNumber;
}
