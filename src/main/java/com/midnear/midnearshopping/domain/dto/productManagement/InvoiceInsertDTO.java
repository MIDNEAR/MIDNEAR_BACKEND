package com.midnear.midnearshopping.domain.dto.productManagement;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceInsertDTO {
    String courier;
    String pickupCourier;
    String pickupInvoice;
    String invoiceNumber;
    Long carrierId;
    List<Long> orderProductId;
    List<Long> returnId;
    List<Long> exchangeId;
}

