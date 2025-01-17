package com.midnear.midnearshopping.mapper.order;

import com.midnear.midnearshopping.domain.dto.order.OrderDTO;
import com.midnear.midnearshopping.domain.dto.order.OrderParamDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderProductsMapper {
//  최신순 필터링
    List<OrderDTO> selectAll(@Param("offset") int offset, @Param("pageSize") int pageSize);

    int count();

//  필터링 검색
    List<OrderDTO> filterSearch(OrderParamDTO orderParamDTO);

    int filterCount(OrderParamDTO orderParamDTO);

}
