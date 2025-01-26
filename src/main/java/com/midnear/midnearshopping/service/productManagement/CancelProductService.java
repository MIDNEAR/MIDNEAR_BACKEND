package com.midnear.midnearshopping.service.productManagement;

import com.midnear.midnearshopping.domain.dto.productManagement.CancelProductDTO;
import com.midnear.midnearshopping.domain.dto.productManagement.ParamDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CancelProductService {

//  전체,최신순 취소요청 띄우기
    List<CancelProductDTO> selectAll(int pageNumber);
    int count();

//  필터링검색
    List<CancelProductDTO> filterSearch(ParamDTO paramDTO);
    int filterCount(ParamDTO paramDTO);

//  선택내역 취소 승인처리
    void confirmCancel(List<Long> canceledProductId);

//  선택내역 취소 거부처리
    void denayCancel(List<Long> canceledProductId);

}
