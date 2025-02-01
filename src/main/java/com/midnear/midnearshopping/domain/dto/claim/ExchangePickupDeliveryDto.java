package com.midnear.midnearshopping.domain.dto.claim;

import lombok.Data;

import java.sql.Date;

//필요할거같아서 만들었는데 안쓰게 되었습니다 근데 혹시 민정언니 이거 필요하면 값 더하거나 뺴서 쓰고 아니면 삭제할게여
@Data
public class ExchangePickupDeliveryDto {
    private String pickupStatus;
    private Date pickupCompleteDate;
    private Long pickupInvoice;
    private String pickupCourier;
    private Integer exchangeId;
}