package com.midnear.midnearshopping.domain.vo.delivery;

import com.midnear.midnearshopping.domain.dto.productManagement.InvoiceInsertDTO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryInfoVO {
    private int deliveryId;
    private String courier;
    private Long invoiceNumber;
    private String trackingDelivery;
    private LocalDate deliveryProcessDate;
    private LocalDate deliveryCompleteDate;
    private int deliveryAddressId;

}
