package com.midnear.midnearshopping.service.productManagement;

import com.midnear.midnearshopping.domain.dto.productManagement.InvoiceInsertDTO;
import com.midnear.midnearshopping.domain.dto.productManagement.ParamDTO;
import com.midnear.midnearshopping.domain.dto.productManagement.ReturnDTO;
import com.midnear.midnearshopping.domain.dto.productManagement.ReturnParamDTO;
import com.midnear.midnearshopping.mapper.productManagement.ReturnMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ReturnServiceImpl implements ReturnService {

    private final ReturnMapper returnMapper;

    private static final int pageSize = 2;

    // 전체, 최신순 정렬
    @Override
    public List<ReturnDTO> selectAll(int pageNumber) {
        int offset = (pageNumber - 1) * pageSize;
        return returnMapper.selectAll(offset, pageSize);
    }

    @Override
    public int count() {
        return returnMapper.count();
    }

    // 필터링 검색
    @Override
    public List<ReturnDTO> filterSearch(ParamDTO paramDTO) {
        int offset = (paramDTO.getPageNumber()- 1) * pageSize;
        paramDTO.setOffset(offset);
        paramDTO.setPageSize(pageSize);
        return returnMapper.filterSearch(paramDTO);
    }

    @Override
    public int filterCount(ParamDTO paramDTO) {
        return returnMapper.filterCount(paramDTO);
    }

    // 선택상품 반품처리
    @Override
    public void confirmReturn(List<Long> returnId) {
        returnMapper.confirmReturn(returnId);
    }

    // 반품거부
    @Override
    public void denayReturn(ReturnParamDTO returnParamDTO) {
        returnMapper.denayReturn(returnParamDTO.getReturnDenayReason(),returnParamDTO.getReturnId());
    }

    // 교환목록에 추가
    @Override
    @Transactional
    public void updateEx(List<Long> returnId) {
        returnMapper.updateEx(returnId);
        returnMapper.insertRetoEx(returnId);
    }

    // 송장번호 입력
    @Override
    public void updateInvoice(InvoiceInsertDTO invoiceInsertDTO) {
        returnMapper.updateReturnStatus(invoiceInsertDTO);
    }

    @Override
    public Long selectCarrierName(String carrierName) {
        return returnMapper.selectCarrierName(carrierName);
    }

    @Override
    public void pickupProduct(List<Long> returnId) {
        returnMapper.pickupProduct(returnId);
    }
}
