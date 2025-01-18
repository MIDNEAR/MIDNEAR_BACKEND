package com.midnear.midnearshopping.domain.vo.products;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Builder
public class ProductsVo {
    private Long productId;
    private String productName;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private String detail;
    private int discountRate;
    private String sizeGuide;
    private Date registeredDate;
    private Date discountStartDate;
    private Date discountEndDate;
    private Long categoryId;

}