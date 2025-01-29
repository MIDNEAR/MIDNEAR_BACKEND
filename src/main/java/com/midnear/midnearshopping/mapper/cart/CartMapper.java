package com.midnear.midnearshopping.mapper.cart;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CartMapper {
    void createCart(Integer userId);

    boolean existByUserId();
    Long findCartIdByUserId(Integer userId);
}
