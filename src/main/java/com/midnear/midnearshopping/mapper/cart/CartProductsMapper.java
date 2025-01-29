package com.midnear.midnearshopping.mapper.cart;

import com.midnear.midnearshopping.domain.dto.cart.CartProductDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CartProductsMapper {
    void addProduct(@Param("cartId") Long cartId, @Param("productColorId") Long productColorId, @Param("quantity") int quantity, @Param("size") String size);
    List<CartProductDto> getCartProducts(@Param("cartId") Long cartId);
    void deleteCartProduct(Long cartProductId);
    void updateQuantity(@Param("cartProductId") Long cartProductId,@Param("quantity") int quantity);
}
