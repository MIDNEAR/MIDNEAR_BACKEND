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
    String resendInvoiceNumber;
    String pickupInvoice;
    String invoiceNumber;
    String resendCourier;
    Long carrierId;
    List<Long> orderProductId;
    List<Long> returnId;
    List<Long> exchangeId;
}

