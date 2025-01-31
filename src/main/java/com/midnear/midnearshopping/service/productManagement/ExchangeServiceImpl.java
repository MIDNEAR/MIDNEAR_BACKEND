package com.midnear.midnearshopping.service.productManagement;

import com.midnear.midnearshopping.domain.dto.productManagement.ExchangeDTO;
import com.midnear.midnearshopping.domain.dto.productManagement.ExchangeParamDTO;
import com.midnear.midnearshopping.domain.dto.productManagement.InvoiceInsertDTO;
import com.midnear.midnearshopping.domain.dto.productManagement.ParamDTO;
import com.midnear.midnearshopping.mapper.productManagement.ExchangeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {

    private final ExchangeMapper exchangeMapper;
    private static final int pageSize = 2;

    // 전체, 최신순 조회
    @Override
    public List<ExchangeDTO> selectAll(int pageNumber) {
        int offset = (pageNumber - 1) * pageSize;
        return exchangeMapper.selectAll(offset, pageSize);
    }

    @Override
    public int count() {
        return exchangeMapper.count();
    }

    // 필터링 검색
    @Override
    public List<ExchangeDTO> filterSearch(ParamDTO paramDTO) {
        int offset = (paramDTO.getPageNumber()- 1) * pageSize;
        paramDTO.setOffset(offset);
        paramDTO.setPageSize(pageSize);
        return exchangeMapper.filterSearch(paramDTO);
    }

    @Override
    public int filterCount(ParamDTO paramDTO) {
        return exchangeMapper.filterCount(paramDTO);
    }

    // 반품으로 변경
    @Transactional
    @Override
    public void updateExchange(ExchangeParamDTO exchangeParamDTO) {
        exchangeMapper.updateExchange(exchangeParamDTO.getExchangeId());
        exchangeMapper.ExchangeToRefund(exchangeParamDTO.getOrderProductId());
    }

    // 선택상품 교환 거부처리
    @Override
    public void denayExchange(ExchangeParamDTO exchangeParamDTO) {
        exchangeMapper.denayExchange(exchangeParamDTO);
    }

    @Override
    public void updatePickupStatus(InvoiceInsertDTO invoiceInsertDTO) {
        exchangeMapper.updatePickupStatus(invoiceInsertDTO);
    }

    @Override
    public Long selectCarrierName(String carrierName) {
        return exchangeMapper.selectCarrierName(carrierName);
    }

    // 상품 재배송처리
    @Transactional
    @Override
    public void updatedelivery(ExchangeParamDTO exchangeParamDTO) {
        exchangeMapper.updateStatus(exchangeParamDTO.getExchangeId());
        exchangeMapper.insertResendInfo(exchangeParamDTO.getExchangeId(),exchangeParamDTO.getResendCourier(),exchangeParamDTO.getResendInvoiceNumber());
    }
}
