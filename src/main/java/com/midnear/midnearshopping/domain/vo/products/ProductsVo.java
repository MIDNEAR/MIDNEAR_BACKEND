package com.midnear.midnearshopping.domain.vo.products;

import com.midnear.midnearshopping.domain.dto.products.ProductsDto;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.sql.Date;

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

    public static ProductsVo toEntity(ProductsDto productsDto) {
        return ProductsVo.builder()
                .productId(productsDto.getProductId())
                .productName(productsDto.getProductName())
                .price(productsDto.getPrice())
                .discountPrice(productsDto.getDiscountPrice())
                .detail(productsDto.getDetail())
                .discountRate(productsDto.getDiscountRate())
                .sizeGuide(productsDto.getSizeGuide())
                .registeredDate(convertToSqlDate(productsDto.getRegisteredDate()))
                .discountStartDate(convertToSqlDate(productsDto.getDiscountStartDate()))
                .discountEndDate(convertToSqlDate(productsDto.getDiscountEndDate()))
                .categoryId(productsDto.getCategoryId())
                .build();
    }

    private static Date convertToSqlDate(String dateString) {
        return (dateString == null || dateString.trim().isEmpty()) ? null : Date.valueOf(dateString);
    }
}