package com.midnear.midnearshopping.domain.dto.Statistics;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatisticsDto {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private Long paymentAmount;
    private Long refundAmount;
}