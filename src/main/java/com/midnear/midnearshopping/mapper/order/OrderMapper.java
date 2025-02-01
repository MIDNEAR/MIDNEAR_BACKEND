package com.midnear.midnearshopping.mapper.order;

import com.midnear.midnearshopping.domain.dto.order.UserOrderCheckDto;
import com.midnear.midnearshopping.domain.dto.order.UserOrderDto;
import com.midnear.midnearshopping.domain.dto.order.UserOrderProductCheckDto;
import com.midnear.midnearshopping.domain.dto.payment.PaymentInfoDto;
import com.midnear.midnearshopping.domain.vo.order.OrderProductsVO;
import com.midnear.midnearshopping.domain.vo.order.OrdersVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {
    Long insertOrder(OrdersVO order);
    Long getOrderIdByOrderNumber(String orderNumber);
    // 주문 기본 정보 조회
    OrdersVO getOrderById(@Param("orderId") Long orderId);
    PaymentInfoDto getPaymentInfoByOrderId(Long orderId);

    List<UserOrderCheckDto> getOrdersByUserId(@Param("userId")Integer userId, @Param("sort") String sort, @Param("offset") int offset, @Param("pageSize") int pageSize);
    Long getOrdersNonUser(@Param("orderName")String orderName, @Param("orderContact") String order_contact, @Param("orderNumber") String orderNumber);
    // 주문 상품 목록 조회


}
