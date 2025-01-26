package com.midnear.midnearshopping.domain.dto.products;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
public class ProductManagementListDto {
    private Long productId;
    private String category;
    private String productName;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private int discountRate;
    private List<ProductColorsListDto> colorsList;
    private Date registerdDate;
}
