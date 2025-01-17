package com.midnear.midnearshopping.domain.dto.productManagement;

import lombok.*;
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
