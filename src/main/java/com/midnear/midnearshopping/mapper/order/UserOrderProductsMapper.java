package com.midnear.midnearshopping.mapper.order;


import com.midnear.midnearshopping.domain.vo.order.OrderProductsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserOrderProductsMapper {
    OrderProductsVO getOrderProductById(Long orderProductId);
    void insertOrderProduct(OrderProductsVO orderProduct);
    List<OrderProductsVO> getOrderProductsByOrderId(@Param("orderId") Long orderId);
    void updateOrderStatus(Long orderProductId, String status);
}
