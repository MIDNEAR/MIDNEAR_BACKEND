package com.midnear.midnearshopping.domain.dto.claim;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ExchangeRequestDto {
    String exchangeReason;
    String exchangeDetailReason;
    String exchangeRequestedOption;
    String exchangeRequestedSize;
    String collectionMethod;
    Long orderProductId;
}
