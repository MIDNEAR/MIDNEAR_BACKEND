package com.midnear.midnearshopping.mapper.productManagement;

import com.midnear.midnearshopping.domain.dto.productManagement.OrderDTO;
import com.midnear.midnearshopping.domain.dto.productManagement.ParamDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderProductsMapper {
//  최신순 필터링
    List<OrderDTO> selectAll(@Param("offset") int offset, @Param("pageSize") int pageSize);

    int count();

//  필터링 검색
    List<OrderDTO> filterSearch(ParamDTO orderParamDTO);

    int filterCount(ParamDTO orderParamDTO);

}
