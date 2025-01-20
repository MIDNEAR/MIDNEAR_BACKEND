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
    Long invoiceNumber;
    List<Long> orderProductId;
}

