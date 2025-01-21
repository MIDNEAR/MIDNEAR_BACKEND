package com.midnear.midnearshopping.service.productManagement;

import com.midnear.midnearshopping.domain.dto.productManagement.InvoiceInsertDTO;
import com.midnear.midnearshopping.domain.dto.productManagement.OptionQuantityDTO;
import com.midnear.midnearshopping.domain.dto.productManagement.OrderShippingDTO;
import com.midnear.midnearshopping.domain.dto.productManagement.ParamDTO;

import com.midnear.midnearshopping.mapper.productManagement.OrderShippingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
public class OrderShippingServiceImpl implements OrderShippingService {

    private final OrderShippingMapper orderShippingMapper;

//  최신순 정렬
    private static final int pageSize = 2;
    @Override
    public List<OrderShippingDTO> selectAll(int pageNumber) {
        int offset = (pageNumber - 1) * pageSize;
        return orderShippingMapper.selectAll(offset, pageSize);
    }

    @Override
    public int count() {
        return orderShippingMapper.count();
    }

//  필터링 검색
    @Override
    public List<OrderShippingDTO> filterSearch(ParamDTO paramDTO) {
        int offset = (paramDTO.getPageNumber()- 1) * pageSize;
        paramDTO.setOffset(offset);
        paramDTO.setPageSize(pageSize);
        return orderShippingMapper.filterSearch(paramDTO);
    }

    @Override
    public int filterCount(ParamDTO paramDTO) {
        return orderShippingMapper.filterCount(paramDTO);
    }

//  발주확인
    @Transactional
    @Override
    public void updateConfirm(List<Long> orderProductId) {
        orderShippingMapper.updateConfirm(orderProductId);
    }

//  송장번호 입력
    @Transactional
    @Override
    public void insertInvoice(InvoiceInsertDTO invoiceInsertDTO) {
        orderShippingMapper.insertInvoice(invoiceInsertDTO);
    }

//  배송지연
    @Override
    public void delaySipping(List<Long> orderProductId) {
        orderShippingMapper.delaySipping(orderProductId);
    }

//  판매가 직접취소
    @Override
    public void directCancel(List<Long> orderProductId) {
        orderShippingMapper.directCancel(orderProductId);
    }

    @Override
    public List<OptionQuantityDTO> selectOptionQuantity(List<Long> orderProductId) {
        return orderShippingMapper.selectOptionQuantity(orderProductId);
    }
}
