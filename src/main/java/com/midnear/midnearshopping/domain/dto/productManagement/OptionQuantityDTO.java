package com.midnear.midnearshopping.domain.dto.productManagement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OptionQuantityDTO {
    Long orderProductId;
    String productName;
    String color;
    String size;
    int quantity;
}
