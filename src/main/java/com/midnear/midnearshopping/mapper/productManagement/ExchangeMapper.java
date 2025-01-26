package com.midnear.midnearshopping.mapper.productManagement;

import com.midnear.midnearshopping.domain.dto.productManagement.ExchangeDTO;
import com.midnear.midnearshopping.domain.dto.productManagement.ParamDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ExchangeMapper {

    // 최신순 필터링
   List<ExchangeDTO> selectAll(@Param("offset") int offset, @Param("pageSize") int pageSize);
   int count();

    // 필터링 검색
    List<ExchangeDTO> filterSearch(ParamDTO paramDTO);
    int filterCount(ParamDTO paramDTO);

    // 선택내역 반품으로 변경
    void updateExchange(@Param("exchangeId")List<Long> exchangeId);
    void ExchangeToRefund(@Param("orderProductId")List<Long> orderProductId);
}
