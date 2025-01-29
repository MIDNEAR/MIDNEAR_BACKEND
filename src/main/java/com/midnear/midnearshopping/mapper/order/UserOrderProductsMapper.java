package com.midnear.midnearshopping.mapper.order;

import com.midnear.midnearshopping.domain.dto.order.UserOrderProductsDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserOrderProductsMapper {
    void insertOrderProduct(UserOrderProductsDto userOrderProductsDto);
}
