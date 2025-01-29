package com.midnear.midnearshopping.domain.dto.claim;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CancelRequestDto {

    String CancelReason;
    Long orderProductId;
}
