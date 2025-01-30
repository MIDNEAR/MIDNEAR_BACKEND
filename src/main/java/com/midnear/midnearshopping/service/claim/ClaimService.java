package com.midnear.midnearshopping.service.claim;

import com.midnear.midnearshopping.domain.dto.claim.CancelRequestDto;
import com.midnear.midnearshopping.domain.dto.claim.ExchangeRequestDto;
import com.midnear.midnearshopping.domain.dto.claim.ReturnRequestDto;
import com.midnear.midnearshopping.domain.vo.claim.CanceledProductVO;
import com.midnear.midnearshopping.domain.vo.claim.ExchangeVO;
import com.midnear.midnearshopping.domain.vo.claim.ReturnsVO;
import com.midnear.midnearshopping.mapper.claim.UserCanceledProductMapper;
import com.midnear.midnearshopping.mapper.claim.UserExchangeMapper;
import com.midnear.midnearshopping.mapper.claim.UserReturnsMapper;
import com.midnear.midnearshopping.mapper.order.UserOrderProductsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClaimService {
    private final UserCanceledProductMapper canceledProductMapper;
    private final UserExchangeMapper userExchangeMapper;
    private final UserReturnsMapper userReturnsMapper;
    private final UserOrderProductsMapper userOrderProductsMapper;

    @Transactional
    public void createCancel(CancelRequestDto cancelRequestDto) {
        try {
            canceledProductMapper.insertCanceledProduct(CanceledProductVO.toEntity(cancelRequestDto));
            userOrderProductsMapper.updateOrderStatus(cancelRequestDto.getOrderProductId(), "취소진행중");
        } catch (Exception e) {
            log.error("주문 취소 요청 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("주문 취소 요청 중 오류가 발생했습니다.");
        }
    }

    @Transactional
    public void createExchange(ExchangeRequestDto exchangeRequestDto) {
        try {
            userExchangeMapper.insertExchange(ExchangeVO.toEntity(exchangeRequestDto));
            userOrderProductsMapper.updateOrderStatus(exchangeRequestDto.getOrderProductId(), "교환진행중");
        } catch (Exception e) {
            log.error("교환 요청 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("교환 요청 중 오류가 발생했습니다.");
        }
    }

    @Transactional
    public void createReturns(ReturnRequestDto returnRequestDto) {
        try {
            userReturnsMapper.insertReturn(ReturnsVO.toEntity(returnRequestDto));
            userOrderProductsMapper.updateOrderStatus(returnRequestDto.getOrderProductId(), "반품진행중");
        } catch (Exception e) {
            log.error("반품 요청 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("반품 요청 중 오류가 발생했습니다.");
        }
    }
}
