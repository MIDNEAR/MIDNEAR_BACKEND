package com.midnear.midnearshopping.service.order;

import com.midnear.midnearshopping.domain.dto.order.UserOrderDto;
import com.midnear.midnearshopping.mapper.order.OrderMapper;
import com.midnear.midnearshopping.mapper.productManagement.OrderProductsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderMapper orderMapper;
    private final OrderProductsMapper orderProductsMapper;

    @Transactional
    public void createOrder(UserOrderDto userOrderDto) {
        orderMapper.insertOrder(userOrderDto);
    }
}

