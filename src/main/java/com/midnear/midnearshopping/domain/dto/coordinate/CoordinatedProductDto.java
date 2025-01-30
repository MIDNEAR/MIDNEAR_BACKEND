package com.midnear.midnearshopping.domain.dto.coordinate;

import com.midnear.midnearshopping.domain.dto.products.SizesDto;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoordinatedProductDto {
    private String category;
    private String productName;
    private String color;
    private List<SizesDto> sizesDtoList;
    private String imageUrl;
    private Date registeredDate;
}
