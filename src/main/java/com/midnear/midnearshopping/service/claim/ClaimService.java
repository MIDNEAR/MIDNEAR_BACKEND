package com.midnear.midnearshopping.service.claim;

import com.midnear.midnearshopping.domain.dto.claim.CancelRequestDto;
import com.midnear.midnearshopping.domain.dto.claim.ExchangeRequestDto;
import com.midnear.midnearshopping.domain.dto.claim.ReturnRequestDto;
import com.midnear.midnearshopping.domain.dto.order.UserOrderCheckDto;
import com.midnear.midnearshopping.domain.dto.order.UserOrderProductCheckDto;
import com.midnear.midnearshopping.domain.vo.claim.CanceledProductVO;
import com.midnear.midnearshopping.domain.vo.claim.ExchangeVO;
import com.midnear.midnearshopping.domain.vo.claim.ReturnsVO;
import com.midnear.midnearshopping.domain.vo.order.OrderProductsVO;
import com.midnear.midnearshopping.mapper.claim.UserCanceledProductMapper;
import com.midnear.midnearshopping.mapper.claim.UserExchangeMapper;
import com.midnear.midnearshopping.mapper.claim.UserReturnsMapper;
import com.midnear.midnearshopping.mapper.delivery.DeliveryAddressMapper;
import com.midnear.midnearshopping.mapper.order.OrderMapper;
import com.midnear.midnearshopping.mapper.order.UserOrderProductsMapper;
import com.midnear.midnearshopping.mapper.products.ProductColorsMapper;
import com.midnear.midnearshopping.mapper.products.ProductImagesMapper;
import com.midnear.midnearshopping.mapper.products.ProductsMapper;
import com.midnear.midnearshopping.mapper.products.SizesMapper;
import com.midnear.midnearshopping.mapper.users.UsersMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClaimService {
    private final UserCanceledProductMapper canceledProductMapper;
    private final UserExchangeMapper userExchangeMapper;
    private final UserReturnsMapper userReturnsMapper;
    private final UserOrderProductsMapper userOrderProductsMapper;
    private final OrderMapper orderMapper;
    private final UserOrderProductsMapper orderProductsMapper;
    private final UsersMapper usersMapper;
    private static final int pageSize = 2;

    @Transactional
    public void createCancel(CancelRequestDto cancelRequestDto) {
        try {
            canceledProductMapper.insertCanceledProduct(CanceledProductVO.toEntity(cancelRequestDto));
            userOrderProductsMapper.updateOrderStatus(cancelRequestDto.getOrderProductId(), "취소진행중");
        } catch (Exception e) {
            log.error("주문 취소 요청 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("주문 취소 요청 중 오류가 발생했습니다.", e);
        }
    }

    @Transactional
    public void createExchange(ExchangeRequestDto exchangeRequestDto) {
        try {
            ExchangeVO vo = ExchangeVO.toEntity(exchangeRequestDto);
            userExchangeMapper.insertExchange(vo);
            userOrderProductsMapper.updateOrderStatus(exchangeRequestDto.getOrderProductId(), "교환진행중");
            userExchangeMapper.insertExchangePickupDelivery(vo.getExchangeId());
        } catch (Exception e) {
            log.error("교환 요청 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("교환 요청 중 오류가 발생했습니다.", e);
        }
    }

    @Transactional
    public void createReturns(ReturnRequestDto returnRequestDto) {
        try {
            userReturnsMapper.insertReturn(ReturnsVO.toEntity(returnRequestDto));
            userOrderProductsMapper.updateOrderStatus(returnRequestDto.getOrderProductId(), "반품진행중");
        } catch (Exception e) {
            log.error("반품 요청 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("반품 요청 중 오류가 발생했습니다.", e);
        }
    }

    @Transactional(readOnly = true)
    public List<UserOrderCheckDto> getClaimOrders(String id, int pageNumber, String sort, String filter) {
        int offset = (pageNumber - 1) * pageSize;

        // 주문 기본 정보 조회
        Integer userId = usersMapper.getUserIdById(id);
        if (userId == null) {
            throw new UsernameNotFoundException("존재하지 않는 유저입니다.");
        }

        List<UserOrderCheckDto> orderCheckDtos = orderMapper.getOrdersByUserId(userId, sort, offset, pageSize);

        // 주문 상품이 없는 주문을 제외한 리스트 생성
        List<UserOrderCheckDto> filteredOrderCheckDtos = new ArrayList<>();

        for (UserOrderCheckDto order : orderCheckDtos) {
            List<OrderProductsVO> orderProducts = orderProductsMapper.getOrderProductsByOrderIdAndStatus(order.getOrderId(), filter);

            // 주문 상품 리스트가 null이면 해당 주문 제거
            if (orderProducts == null) {
                continue;
            }

            // 주문 상품 리스트에서 null인 요소 제거
            List<OrderProductsVO> validOrderProducts = orderProducts.stream()
                    .filter(Objects::nonNull) // null인 요소 제거
                    .collect(Collectors.toList());

            // 주문 상품이 모두 null이었거나 비어있다면 해당 주문 제거
            if (validOrderProducts.isEmpty()) {
                continue;
            }

            List<UserOrderProductCheckDto> userOrderProductCheckDtos = validOrderProducts.stream()
                    .map(product -> {
                        BigDecimal payPrice = product.getProductPrice()
                                .subtract(product.getPointDiscount() != null ? product.getPointDiscount() : BigDecimal.ZERO)
                                .subtract(product.getCouponDiscount() != null ? product.getCouponDiscount() : BigDecimal.ZERO);

                        return UserOrderProductCheckDto.builder()
                                .orderProductId(product.getOrderProductId())
                                .size(product.getSize())
                                .quantity(product.getQuantity())
                                .orderStatus(product.getClaimStatus())
                                .pointDiscount(product.getPointDiscount())
                                .payPrice(payPrice) // 계산된 값 설정
                                .productName(product.getProductName())
                                .productMainImage(product.getProductMainImage())
                                .build();
                    }).collect(Collectors.toList());

            order.setUserOrderProductCheckDtos(userOrderProductCheckDtos);
            filteredOrderCheckDtos.add(order); // 유효한 주문만 리스트에 추가
        }

        return filteredOrderCheckDtos;
    }


}
