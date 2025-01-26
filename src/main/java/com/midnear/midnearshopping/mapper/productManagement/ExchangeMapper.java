package com.midnear.midnearshopping.mapper.productManagement;

import com.midnear.midnearshopping.domain.dto.productManagement.ExchangeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ExchangeMapper {

    // 최신순 필터링
   List<ExchangeDTO> selectAll(@Param("offset") int offset, @Param("pageSize") int pageSize);
   int count();


}
