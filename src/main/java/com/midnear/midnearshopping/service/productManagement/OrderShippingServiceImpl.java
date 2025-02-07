package com.midnear.midnearshopping.service.productManagement;

import com.midnear.midnearshopping.domain.dto.productManagement.*;

import com.midnear.midnearshopping.domain.vo.delivery.DeliveryInfoVO;
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
            for (Long orderProductIds : orderProductId) {

                DeliveryInfoVO deliveryInfoVO = new DeliveryInfoVO();
                deliveryInfoVO.setTrackingDelivery("배송전");
                orderShippingMapper.insertDeliveryInfo(deliveryInfoVO);

                Long deliveryId = deliveryInfoVO.getDeliveryId();

                orderShippingMapper.updateDeliveryId(deliveryId, orderProductIds);
            }

    }

//  송장번호 입력
    @Transactional
    @Override
    public void insertInvoice(InvoiceInsertDTO invoiceInsertDTO) {
        orderShippingMapper.insertInvoice(invoiceInsertDTO);
    }

    @Override
    public Long selectCarrierName(String carrierName) {
        return orderShippingMapper.selectCarrierName(carrierName);
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
    public List<Long> selectCancelProduct(List<Long> orderProductId) {
        return orderShippingMapper.selectCancelProduct(orderProductId);
    }

    //  선택건 옵션별 주문수량 엑셀로
    @Override
    public List<OptionQuantityDTO> selectOptionQuantity(List<Long> orderProductId) {
        return orderShippingMapper.selectOptionQuantity(orderProductId);
    }

//  선택건 주문서 출력
    @Override
    public List<OrderReciptDTO> selectOrderRecipt(List<Long> orderProductId) {
        return orderShippingMapper.selectOrderRecipt(orderProductId);
    }

    @Override
    public List<OptionQuantityDTO> selectOrderDetails(List<Long> orderProductId) {
        return orderShippingMapper.selectOrderDetails(orderProductId);
    }
}
