package com.midnear.midnearshopping.domain.vo.claim;

import java.math.BigDecimal;
import java.util.Date;

public class ClaimListDto {
    //String claimKind; //클레임 종류
    private Long orderProductId;
    private String size;
    private int quantity;
    private String claimStatus;
    private BigDecimal pointDiscount;
    private BigDecimal payPrice; // 최종 구매가
    private String productName;
    private String productMainImage;
}
