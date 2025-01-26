package com.midnear.midnearshopping.domain.dto.productManagement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeParamDTO {
    List<Long> exchangeId;
    List<Long> orderProductId;
    String exchangeDenayReason;
}
