package com.midnear.midnearshopping.service.order;

import com.midnear.midnearshopping.domain.dto.order.OrderDTO;
import com.midnear.midnearshopping.mapper.order.OrderProductsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderProductServiceImpl implements OrderProductService {

    private final OrderProductsMapper orderProductsMapper;

    private static final int pageSize = 2;

    @Override
    public List<OrderDTO> selectAll(int pageNumber) {
        int offset = (pageNumber - 1) * pageSize;
        return orderProductsMapper.selectAll(offset, pageSize);
    }

    @Override
    public int count() {
        return orderProductsMapper.count();
    }
}
