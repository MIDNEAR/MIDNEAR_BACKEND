package com.midnear.midnearshopping.mapper.productManagement;

import com.midnear.midnearshopping.domain.dto.productManagement.ExchangeDTO;
import com.midnear.midnearshopping.domain.dto.productManagement.ExchangeParamDTO;
import com.midnear.midnearshopping.domain.dto.productManagement.InvoiceInsertDTO;
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

    // 선택내역 교환 거부처리
    void denayExchange(@Param("exchangeParamDTO") ExchangeParamDTO exchangeParamDTO);


    // 수거 송장번호 입력
    void updatePickupStatus(InvoiceInsertDTO invoiceInsertDTO);
    Long selectCarrierName(String pickupCourier);

    // 선택내역 배송처리
    void updateStatus(@Param("exchangeId")List<Long> exchangeId);
    void insertResendInfo(@Param("exchangeId")List<Long>exchangeId,@Param("resendCourier")String resendCourier,@Param("resendInvoiceNumber")Long resendInvoiceNumber);
}
