package com.midnear.midnearshopping.mapper.productManagement;

import com.midnear.midnearshopping.domain.dto.productManagement.ReturnDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReturnMapper {
    // 전체, 최신순 조회
    List<ReturnDTO> selectAll(@Param("offset") int offset, @Param("pageSize") int pageSize);
    int count();


}
