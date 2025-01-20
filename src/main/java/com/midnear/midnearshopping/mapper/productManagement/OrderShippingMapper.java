package com.midnear.midnearshopping.mapper.productManagement;

import com.midnear.midnearshopping.domain.dto.productManagement.OrderShippingDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderShippingMapper {

//  전체, 최신순 정렬
    List<OrderShippingDTO> selectAll(@Param("offset") int offset, @Param("pageSize") int pageSize);
    int count();
}
