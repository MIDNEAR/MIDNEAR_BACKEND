package com.midnear.midnearshopping.domain.dto.productManagement;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParamDTO {
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
