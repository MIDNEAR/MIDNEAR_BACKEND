package com.midnear.midnearshopping.domain.vo.claim;

import com.midnear.midnearshopping.domain.dto.claim.ExchangeRequestDto;
import lombok.*;
import okhttp3.internal.connection.Exchange;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ExchangeVO {
    Long exchangeId;
    String exchangeStatus;
    String exchangeReason;
    String exchangeDetailReason;
    String exchangeRequestedOption;
    String exchangeRequestedSize;
    Date exchangeRequestDate;
    String collectionMethod;
    Long orderProductId;
    public static ExchangeVO toEntity(ExchangeRequestDto exchangeRequestDto) {
        return ExchangeVO.builder()
                .exchangeStatus("교환요청")
                .exchangeReason(exchangeRequestDto.getExchangeReason())
                .exchangeDetailReason(exchangeRequestDto.getExchangeDetailReason())
                .exchangeRequestedOption(exchangeRequestDto.getExchangeRequestedOption())
                .exchangeRequestedSize(exchangeRequestDto.getExchangeRequestedSize())
                .exchangeRequestDate(new Date()) // 현재 시간 설정
                .collectionMethod(exchangeRequestDto.getCollectionMethod())
                .orderProductId(exchangeRequestDto.getOrderProductId())
                .build();
    }
}
