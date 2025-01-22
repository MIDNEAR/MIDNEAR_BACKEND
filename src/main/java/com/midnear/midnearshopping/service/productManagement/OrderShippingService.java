package com.midnear.midnearshopping.service.productManagement;

import com.midnear.midnearshopping.domain.dto.productManagement.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface OrderShippingService {
// 주문상품 전체 , 최신순 정렬
   List<OrderShippingDTO> selectAll(int pageNumber);
   int count();

// 필터링 검색
   List<OrderShippingDTO> filterSearch(ParamDTO paramDTO);
   int filterCount(ParamDTO paramDTO);

   @Transactional
// 발주확인
   void updateConfirm(List<Long> orderProductId);

   @Transactional
// 송장번호 입력
   void insertInvoice(InvoiceInsertDTO invoiceInsertDTO);

// 배송지연
   @Transactional
   void delaySipping(List<Long> orderProductId);

// 판매자 직접취소
   @Transactional
   void directCancel(List<Long> orderProductId);

// 옵션별 주문수량 보기
   List<OptionQuantityDTO> selectOptionQuantity(List<Long> orderProductId);

// 선택건 주문서 출력
   List<OrderReciptDTO> selectOrderRecipt(List<Long> orderProductId);
}
