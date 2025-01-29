package com.midnear.midnearshopping.domain.vo.cart;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.annotations.Mapper;

@Getter
@Setter
public class CartProductsVO {
    private Long cartId;
    private Long cartProductId;
    private Long productColorId;
    private Integer productQuantity;
    private String size;
}
