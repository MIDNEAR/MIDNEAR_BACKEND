package com.midnear.midnearshopping.mapper.order;

import com.midnear.midnearshopping.domain.dto.order.UserOrderDto;
import com.midnear.midnearshopping.domain.vo.order.OrdersVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
    Long insertOrder(OrdersVO order);
    Long getOrderIdByOrderNumber(String orderNumber);
}
