package com.midnear.midnearshopping.domain.dto.coordinate;

import com.midnear.midnearshopping.domain.dto.products.SizesDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ColorDto {
    private String color;
    private List<SizesDto> sizesDtoList;
    private String imageUrl;
    private Long productColorId;
    private List<CoordinatedProductDto> coordinatedProductDtoList;
}
