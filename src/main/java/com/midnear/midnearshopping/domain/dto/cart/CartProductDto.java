package com.midnear.midnearshopping.domain.dto.cart;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
public class CartProductDto {
    private Long cartId;
    //여가서부터
    private Long productColorId;
    private Integer productQuantity;
    private String size;
    private Long cartProductsId;
    //여기까지는 cart_product테이블에서 가져와야하고
    //여기서부터
    private String productName;
    private BigDecimal price;
    private Integer discountRate;
    private BigDecimal discountPrice;
    private Date discountStartDate;
    private Date discountEndDate;
    //여기까지는 product 테이블에서 가져와야
    private String productImage; // 얘는 product_images테이블에서 가져와야하고
    private String color;
}
