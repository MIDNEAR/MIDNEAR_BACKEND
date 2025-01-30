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
public class MainProductDto {
    private String category;
    private String productName;
    private List<ColorDto> colorList;
    private List<CoordinatedProductDto> coordinatedProductDtoList;
}
