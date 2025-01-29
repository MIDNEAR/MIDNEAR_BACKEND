package com.midnear.midnearshopping.domain.dto.cart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartProductDto {
    private Long cartId;
    //여가서부터
    private Long productColorId;
    private Integer productQuantity;
    private String size;
    //여기까지는 cart_product테이블에서 가져와야하고
    //여기서부터
    private String productName;
    private String price;
    //여기까지는 product 테이블에서 가져와야
    private String productImage; // 얘는 product_images테이블에서 가져와야하고
}
