package com.midnear.midnearshopping.service.productManagement;
import com.midnear.midnearshopping.domain.dto.productManagement.InvoiceInsertDTO;
import com.midnear.midnearshopping.domain.dto.productManagement.ParamDTO;
import com.midnear.midnearshopping.domain.dto.productManagement.ReturnDTO;
import com.midnear.midnearshopping.domain.dto.productManagement.ReturnParamDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReturnService {
    // 전체 , 최신순 정렬
    List<ReturnDTO> selectAll(int pageNumber);
    int count();

    // 필터링 검색
    List<ReturnDTO> filterSearch(ParamDTO paramDTO);
    int filterCount(ParamDTO paramDTO);

    // 선택상품 반품 처리
    void confirmReturn(List<Long> returnId);

    // 선택상품 반품 거부처리
    void denayReturn(ReturnParamDTO returnParamDTO);

    // 선택상품 교환처리
    void updateEx(List<Long> returnId);

    // 반품 송장번호 입력
    void updateInvoice(InvoiceInsertDTO invoiceInsertDTO);
    Long selectCarrierName(String carrierName);
}
