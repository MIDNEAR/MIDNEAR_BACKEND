package com.midnear.midnearshopping.domain.dto.products;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ProductsListDto {
    //목록:
    //상품 아이디
    //사진 두개
    //상품명
    //정가
    //할인가
    //적용 쿠폰
    private Long colorId;
    private String productName;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private int discountRate;
    private Date discountStartDate;
    private Date discountEndDate;
    private String saleStatus;
    private Long categoryId;
    private String frontImageUrl;
    private String backImageUrl;
}

