package com.midnear.midnearshopping.service.productManagement;

import com.midnear.midnearshopping.domain.dto.productManagement.ConfirmPurchaseDTO;
import com.midnear.midnearshopping.domain.dto.productManagement.ParamDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ConfirmPurchaseService {

 // 구매 확정내역 최신순 띄우기
    List<ConfirmPurchaseDTO> selectAll(int pageNumber);
    int count();

// 구매 확정내역 필터링 검색
    List<ConfirmPurchaseDTO> filterSearch(ParamDTO paramDTO);
    int filterCount(ParamDTO paramDTO);

}
