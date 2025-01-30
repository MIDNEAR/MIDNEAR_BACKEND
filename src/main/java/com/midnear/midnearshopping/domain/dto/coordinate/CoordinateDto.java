package com.midnear.midnearshopping.domain.dto.coordinate;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoordinateDto {
    private Long originalProductId;
    private Long coordinatedProductId;
}
