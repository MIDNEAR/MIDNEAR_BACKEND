package com.midnear.midnearshopping.domain.dto.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.micrometer.common.lang.Nullable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Null;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderParamDTO {
    @Builder.Default
    int pageNumber = 1;
    int pageSize;
    int offset;
    String searchPeriod;
    String dateFilter;
    LocalDateTime startDate;
    LocalDateTime endDate;
    String searchCondition;
    String searchValue;
}
