package com.midnear.midnearshopping.service.productManagement;

import com.midnear.midnearshopping.domain.dto.productManagement.InvoiceInsertDTO;
import com.midnear.midnearshopping.domain.dto.productManagement.OrderShippingDTO;
import com.midnear.midnearshopping.domain.dto.productManagement.ParamDTO;
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
}
