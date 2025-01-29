package com.midnear.midnearshopping.mapper.order;

import com.midnear.midnearshopping.domain.dto.order.UserOrderDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
    void insertOrder(UserOrderDto order);
}
