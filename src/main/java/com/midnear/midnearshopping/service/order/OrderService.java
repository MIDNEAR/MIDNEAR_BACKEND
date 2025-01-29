package com.midnear.midnearshopping.service.order;

import com.midnear.midnearshopping.domain.dto.delivery.DeliveryAddrDto;
import com.midnear.midnearshopping.domain.dto.order.UserOrderDto;
import com.midnear.midnearshopping.domain.vo.order.OrderProductsVO;
import com.midnear.midnearshopping.domain.vo.order.OrdersVO;
import com.midnear.midnearshopping.domain.vo.products.ProductsVo;
import com.midnear.midnearshopping.mapper.delivery.DeliveryAddressMapper;
import com.midnear.midnearshopping.mapper.order.OrderMapper;
import com.midnear.midnearshopping.mapper.order.UserOrderProductsMapper;
import com.midnear.midnearshopping.mapper.products.ProductColorsMapper;
import com.midnear.midnearshopping.mapper.products.ProductImagesMapper;
import com.midnear.midnearshopping.mapper.products.ProductsMapper;
import com.midnear.midnearshopping.mapper.users.UsersMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderMapper orderMapper;
    private final UserOrderProductsMapper orderProductsMapper;
    private final DeliveryAddressMapper deliveryAddressMapper;
    private final UsersMapper usersMapper;
    private final ProductsMapper productsMapper;
    private final ProductImagesMapper productImagesMapper;
    private final ProductColorsMapper productColorsMapper;

    @Transactional
    public void createOrder(String id, UserOrderDto userOrderDto) {
        Integer userId = usersMapper.getUserIdById(id);
        DeliveryAddrDto deliveryAddr = deliveryAddressMapper.getDeliveryAddressById(userOrderDto.getDeliveryAddrId());
        OrdersVO ordersVO = OrdersVO.builder()
                .orderDate(new Date()) // 현재 날짜 설정
                .orderName(userOrderDto.getOrderName())
                .orderContact(userOrderDto.getOrderContact())
                .orderEmail(userOrderDto.getOrderEmail())
                .recipientName(deliveryAddr.getRecipient())
                .postalCode(deliveryAddr.getPostalCode())
                .address(deliveryAddr.getAddress())
                .detailedAddress(deliveryAddr.getDetailAddress())
                .orderNumber(OrderNumberGenerator.generateOrderNumber())
                .userId(userId)
                .allPayment(userOrderDto.getAllPayment())
                .build();
        orderMapper.insertOrder(ordersVO);
        Long orderId = ordersVO.getOrderId();
        if (orderId == null) {
            throw new RuntimeException("주문 생성 실패: orderId가 NULL입니다.");
        }
        System.out.println("📌 생성된 주문 ID: " + orderId);

        List<OrderProductsVO> orderProductsList = userOrderDto.getOderProductsRequestDtos().stream().map(dto -> {
            String mainImage = productImagesMapper.getImageUrlsById(dto.getProductColorId()).get(0);
            Long productId = productColorsMapper.getProductIdByColor(dto.getProductColorId());
            ProductsVo productVO = productsMapper.getProductById(productId);
            String color = productColorsMapper.getColorById(dto.getProductColorId());
            return OrderProductsVO.builder()
                    .orderId(orderId)
                    .size(dto.getSize())
                    .quantity(dto.getQuantity())
                    .couponDiscount(dto.getCouponDiscount())
                    .buyConfirmDate(null) // 구매 확정 날짜는 null... 이거 맞죠?
                    .claimStatus("0") // 데이터 넣어져있던거 보니까 이런식 같아서..
                    .pointDiscount(dto.getPointDiscount()) // 포인트 할인 매핑
                    .deliveryId(null) // 배송지 ID,, 이건 추후에 넣는거 맞죠? 관리자 페이지에서
                    .price(dto.getPrice()) // 가격 매핑
                    .productName(productVO.getProductName()) // 제품명 가져오기
                    .color(color) // 색상 정보 가져오기
                    .deliveryCharge(dto.getDeliveryCharge()) // 배송비 매핑
                    .productMainImage(mainImage) // 제품 대표 이미지 매핑
                    .build();
        }).toList();

        for (OrderProductsVO orderProduct : orderProductsList) {
            orderProductsMapper.insertOrderProduct(orderProduct);
        }
    }


}

