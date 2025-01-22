package com.midnear.midnearshopping.mapper.productManagement;

import com.midnear.midnearshopping.domain.dto.productManagement.CancelProductDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CancelProductMapper {
    List<CancelProductDTO> selectAll(@Param("offset") int offset, @Param("pageSize") int pageSize);

    int count();
}
