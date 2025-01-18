package com.midnear.midnearshopping.service.productManagement;

import com.midnear.midnearshopping.domain.dto.productManagement.OrderDTO;
import com.midnear.midnearshopping.domain.dto.productManagement.ParamDTO;
import com.midnear.midnearshopping.mapper.productManagement.OrderProductsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderProductServiceImpl implements OrderProductService {

    private final OrderProductsMapper orderProductsMapper;

    private static final int pageSize = 2;
//  전체 최신순 조회
    @Override
    public List<OrderDTO> selectAll(int pageNumber) {
        int offset = (pageNumber - 1) * pageSize;
        return orderProductsMapper.selectAll(offset, pageSize);
    }

    @Override
    public int count() {
        return orderProductsMapper.count();
    }

//  필터링 검색
    @Override
    public List<OrderDTO> filterSearch(ParamDTO orderParamDTO) {
        int offset = (orderParamDTO.getPageNumber()- 1) * pageSize;
        orderParamDTO.setOffset(offset);
        orderParamDTO.setPageSize(pageSize);
        return orderProductsMapper.filterSearch(orderParamDTO);
    }

    @Override
    public int filterCount(ParamDTO orderParamDTO) {
        return orderProductsMapper.filterCount(orderParamDTO);
    }
}
