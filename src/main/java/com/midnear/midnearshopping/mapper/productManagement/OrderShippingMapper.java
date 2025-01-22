package com.midnear.midnearshopping.mapper.productManagement;

import com.midnear.midnearshopping.domain.dto.productManagement.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderShippingMapper {

//  전체, 최신순 정렬
    List<OrderShippingDTO> selectAll(@Param("offset") int offset, @Param("pageSize") int pageSize);
    int count();

//  필터링 조회
    List<OrderShippingDTO> filterSearch(ParamDTO paramDTO);
    int filterCount(ParamDTO orderParamDTO);

//  발주확인
    void updateConfirm(@Param("orderProductId")List<Long> orderProductId);

//  송장번호 입력
    void insertInvoice(InvoiceInsertDTO invoiceInsertDTO);

//  발송지연
    void delaySipping(@Param("orderProductId")List<Long> orderProductId);

//  판매자 직접 취소
    void directCancel(@Param("orderProductId")List<Long> orderProductId);

//  옵션별 주문수량 보기
     List<OptionQuantityDTO> selectOptionQuantity(@Param("orderProductId")List<Long> orderProductId);

//  선택건 주문서 출력
    List<OrderReciptDTO> selectOrderRecipt(@Param("orderProductId")List<Long> orderProductId);
}
