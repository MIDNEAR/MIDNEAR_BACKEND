package com.midnear.midnearshopping.mapper.productManagement;

import com.midnear.midnearshopping.domain.dto.productManagement.ParamDTO;
import com.midnear.midnearshopping.domain.dto.productManagement.ReturnDTO;
import com.midnear.midnearshopping.domain.dto.productManagement.ReturnParamDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReturnMapper {
    // 전체, 최신순 조회
    List<ReturnDTO> selectAll(@Param("offset") int offset, @Param("pageSize") int pageSize);
    int count();

    // 필터링 검색
    List<ReturnDTO> filterSearch(ParamDTO paramDTO);
    int filterCount(ParamDTO paramDTO);

    // 선택주문 반품처리
    void confirmReturn(List<Long> returnId);

    // 선택주문 반품 거부처리
    void denayReturn(String returnDenayReason, List<Long> returnId);
}
