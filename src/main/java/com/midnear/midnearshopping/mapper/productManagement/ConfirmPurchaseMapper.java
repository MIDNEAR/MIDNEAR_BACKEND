package com.midnear.midnearshopping.mapper.productManagement;

import com.midnear.midnearshopping.domain.dto.productManagement.ConfirmPurchaseDTO;
import com.midnear.midnearshopping.domain.dto.productManagement.ParamDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ConfirmPurchaseMapper {

//  최신순 필터링
    List<ConfirmPurchaseDTO> selectAll(@Param("offset") int offset, @Param("pageSize") int pageSize);
    int count();

//  필터링 조회
    List<ConfirmPurchaseDTO> filterSearch(ParamDTO paramDTO);
    int filterCount(ParamDTO paramDTO);
}
