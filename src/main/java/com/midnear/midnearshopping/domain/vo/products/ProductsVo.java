package com.midnear.midnearshopping.domain.vo.products;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Builder
public class ProductsVo {
    private Long productId;
    private String product_name;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private  int discountRate;
    private Date discountStartDate;
    private Date discountEndDate;
    private String detail;
    private String sizeGuide;
    private Date registeredDate;
    private Long categoryId;

}