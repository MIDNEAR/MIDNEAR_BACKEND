package com.midnear.midnearshopping.mapper.order;

import com.midnear.midnearshopping.domain.dto.order.OrderDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderProductsMapper {
    List<OrderDTO> selectAll(@Param("offset") int offset, @Param("pageSize") int pageSize);

    int count();
}
