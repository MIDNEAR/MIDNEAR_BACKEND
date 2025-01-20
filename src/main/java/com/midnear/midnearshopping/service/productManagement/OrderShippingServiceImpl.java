package com.midnear.midnearshopping.service.productManagement;

import com.midnear.midnearshopping.domain.dto.productManagement.OrderShippingDTO;
import com.midnear.midnearshopping.mapper.productManagement.OrderProductsMapper;
import com.midnear.midnearshopping.mapper.productManagement.OrderShippingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class OrderShippingServiceImpl implements OrderShippingService {

    private final OrderShippingMapper orderShippingMapper;

//  주문상품 전체, 최신순 정렬
    private static final int pageSize = 2;
    @Override
    public List<OrderShippingDTO> selectAll(int pageNumber) {
        int offset = (pageNumber - 1) * pageSize;
        return orderShippingMapper.selectAll(offset, pageSize);
    }

    @Override
    public int count() {
        return orderShippingMapper.count();
    }
}
