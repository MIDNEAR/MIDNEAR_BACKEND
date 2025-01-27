package com.midnear.midnearshopping.service.productManagement;

import com.midnear.midnearshopping.domain.dto.productManagement.ExchangeDTO;
import com.midnear.midnearshopping.domain.dto.productManagement.ExchangeParamDTO;
import com.midnear.midnearshopping.domain.dto.productManagement.ParamDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExchangeService {

//  최신순 필터링
    List<ExchangeDTO> selectAll(int pageSize);
    int count();

//  필터링 검색
    List<ExchangeDTO> filterSearch(ParamDTO paramDTO);
    int filterCount(ParamDTO paramDTO);

//  선택내역 반품으로 변경
    void updateExchange(ExchangeParamDTO exchangeParamDTO);

//  선택내역 교환 거부처리
    void denayExchange(ExchangeParamDTO exchangeParamDTO);

//  선택내역 배송처리
    void updatedelivery(ExchangeParamDTO exchangeParamDTO);
}
