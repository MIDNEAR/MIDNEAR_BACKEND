package com.midnear.midnearshopping.domain.dto.products;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ProductsDetailDto {
    private Long productId;
    private String productName;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private int discountRate;
    private Date discountStartDate;
    private Date discountEndDate;
    private String detail;
    private String sizeGuide;
    private Date registeredDate;
    private Long categoryId;
    private List<ProductDetailColorDto> colors;
    private List<String> images;
}
