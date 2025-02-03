package com.midnear.midnearshopping.mapper.cart;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CartMapper {
    void createCart(Long userId);

    boolean existByUserId(Long userId);
    Long findCartIdByUserId(Long userId);
}
