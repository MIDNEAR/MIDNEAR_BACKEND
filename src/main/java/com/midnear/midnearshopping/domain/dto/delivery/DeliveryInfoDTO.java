package com.midnear.midnearshopping.domain.dto.delivery;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryInfoDTO {
    Long deliveryId;
    Long returnId;
    Long exchangeId;
    String pickupInvoice;
    String carrierCode;
    String invoiceNumber;
    String resendInvoiceNumber;
}
