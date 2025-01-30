package com.midnear.midnearshopping.service.order;

import com.midnear.midnearshopping.domain.dto.delivery.DeliveryAddrDto;
import com.midnear.midnearshopping.domain.dto.order.*;
import com.midnear.midnearshopping.domain.vo.order.OrderProductsVO;
import com.midnear.midnearshopping.domain.vo.order.OrdersVO;
import com.midnear.midnearshopping.domain.vo.products.ProductsVo;
import com.midnear.midnearshopping.domain.vo.products.SizesVo;
import com.midnear.midnearshopping.mapper.delivery.DeliveryAddressMapper;
import com.midnear.midnearshopping.mapper.order.OrderMapper;
import com.midnear.midnearshopping.mapper.order.UserOrderProductsMapper;
import com.midnear.midnearshopping.mapper.products.ProductColorsMapper;
import com.midnear.midnearshopping.mapper.products.ProductImagesMapper;
import com.midnear.midnearshopping.mapper.products.ProductsMapper;
import com.midnear.midnearshopping.mapper.products.SizesMapper;
import com.midnear.midnearshopping.mapper.users.UsersMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    private final SizesMapper sizesMapper;
    private static final int pageSize = 2;

    @Transactional(rollbackFor = Exception.class)
    public void createOrder(String id, UserOrderDto userOrderDto) {
        // 사용자 ID 조회
        Integer userId = usersMapper.getUserIdById(id);
        if (userId == null) {
            throw new RuntimeException("해당 사용자를 찾을 수 없습니다. 사용자 ID: " + id);
        }

        // 배송지 정보 조회
        DeliveryAddrDto deliveryAddr = deliveryAddressMapper.getDeliveryAddressById(userOrderDto.getDeliveryAddrId());
        if (deliveryAddr == null) {
            throw new RuntimeException("배송지 정보를 찾을 수 없습니다. 배송지 ID: " + userOrderDto.getDeliveryAddrId());
        }

        // 주문 정보 생성
        OrdersVO ordersVO = OrdersVO.builder()
                .orderDate(new Date())
                .orderName(userOrderDto.getOrderName())
                .orderContact(userOrderDto.getOrderContact())
                .orderEmail(userOrderDto.getOrderEmail())
                .recipientName(deliveryAddr.getRecipient())
                .recipientContact(deliveryAddr.getRecipientContact())
                .postalCode(deliveryAddr.getPostalCode())
                .address(deliveryAddr.getAddress())
                .detailedAddress(deliveryAddr.getDetailAddress())
                .orderNumber(OrderNumberGenerator.generateOrderNumber())
                .userId(userId)
                .allPayment(userOrderDto.getAllPayment())
                .build();

        // 주문 정보 DB 저장
        orderMapper.insertOrder(ordersVO);

        Long orderId = ordersVO.getOrderId();
        if (orderId == null) {
            throw new RuntimeException("주문 생성 실패: orderId가 NULL입니다.");
        }
        System.out.println("📌 생성된 주문 ID: " + orderId);

        // 주문 상품 정보 생성
        List<OrderProductsVO> orderProductsList = userOrderDto.getOderProductsRequestDtos().stream().map(dto -> {
            // 상품 이미지 조회
            List<String> images = productImagesMapper.getImageUrlsById(dto.getProductColorId());
            if (images == null || images.isEmpty()) {
                throw new RuntimeException("해당 상품의 이미지가 없습니다. 상품 ID: " + dto.getProductColorId());
            }
            String mainImage = images.get(0);

            // 상품 정보 조회
            Long productId = productColorsMapper.getProductIdByColor(dto.getProductColorId());
            if (productId == null) {
                throw new RuntimeException("해당 색상에 대한 제품 ID를 찾을 수 없습니다. 상품 색상 ID: " + dto.getProductColorId());
            }

            ProductsVo productVO = productsMapper.getProductById(productId);
            if (productVO == null) {
                throw new RuntimeException("해당 상품 정보를 찾을 수 없습니다. 상품 ID: " + productId);
            }

            // 상품 색상 조회
            String color = productColorsMapper.getColorById(dto.getProductColorId());

            // 현재 재고 조회 (트랜잭션 내에서 Lock)
            int stock = sizesMapper.getStockByColorAndSize(dto.getProductColorId(), dto.getSize());
            if (stock - dto.getQuantity() < 0) {
                throw new RuntimeException("해당 상품은 현재 재고 부족으로 구매가 불가능합니다. 상품명: " + productVO.getProductName());
            }

            // 재고 차감
            sizesMapper.updateSizeByColorAndSize(dto.getProductColorId(), dto.getSize(), dto.getQuantity());

            return OrderProductsVO.builder()
                    .orderId(orderId)
                    .size(dto.getSize())
                    .quantity(dto.getQuantity())
                    .couponDiscount(dto.getCouponDiscount())
                    .buyConfirmDate(null)
                    .claimStatus("0")
                    .pointDiscount(dto.getPointDiscount())
                    .deliveryId(null)
                    .productPrice(dto.getProductPrice())
                    .productName(productVO.getProductName())
                    .color(color)
                    .deliveryCharge(dto.getDeliveryCharge())
                    .productMainImage(mainImage)
                    .build();
        }).toList();

        // 주문 상품 정보 DB 저장
        for (OrderProductsVO orderProduct : orderProductsList) {
            orderProductsMapper.insertOrderProduct(orderProduct);
        }


    }

    @Transactional(readOnly = true)
    public List<UserOrderCheckDto> getOrders(String id, int pageNumber, String sort) {
        int offset = (pageNumber - 1) * pageSize;
        // 주문 기본 정보 조회
        Integer userId = usersMapper.getUserIdById(id);
        if (userId == null) {
            throw new UsernameNotFoundException("존재하지 않는 유저입니다.");
        }
        List<UserOrderCheckDto> orderCheckDtos = orderMapper.getOrdersByUserId(userId, sort, offset, pageSize);

        // 각 주문에 대해 주문 상품 목록 조회 및 payPrice 계산
        for (UserOrderCheckDto order : orderCheckDtos) {
            List<OrderProductsVO> orderProducts = orderProductsMapper.getOrderProductsByOrderId(order.getOrderId());

            List<UserOrderProductCheckDto> userOrderProductCheckDtos = orderProducts.stream()
                    .map(product -> {
                        BigDecimal payPrice = product.getProductPrice()
                                .subtract(product.getPointDiscount() != null ? product.getPointDiscount() : BigDecimal.ZERO)
                                .subtract(product.getCouponDiscount() != null ? product.getCouponDiscount() : BigDecimal.ZERO);

                        return UserOrderProductCheckDto.builder()
                                .orderProductId(product.getOrderProductId())
                                .size(product.getSize())
                                .quantity(product.getQuantity())
                                .claimStatus(product.getClaimStatus())
                                .pointDiscount(product.getPointDiscount())
                                .payPrice(payPrice) // 계산된 값 설정
                                .productName(product.getProductName())
                                .productMainImage(product.getProductMainImage())
                                .build();
                    }).collect(Collectors.toList());

            order.setUserOrderProductCheckDtos(userOrderProductCheckDtos);
        }

        return orderCheckDtos;
    }

    @Transactional(readOnly = true)
    public OrderDetailsDto getOrderDetails(Long orderId) {
        // 주문 기본 정보 조회
        OrdersVO order = orderMapper.getOrderById(orderId);
        if (order == null) {
            throw new RuntimeException("주문을 찾을 수 없습니다. (orderId: " + orderId + ")");
        }

        // 해당 주문의 주문 상품 목록 조회
        List<OrderProductsVO> orderProducts = orderProductsMapper.getOrderProductsByOrderId(orderId);

        // 주문 상품 목록을 DTO로 변환하면서 payPrice 계산
        List<UserOrderProductCheckDto> userOrderProductCheckDtos = orderProducts.stream()
                .map(product -> {
                    BigDecimal payPrice = product.getProductPrice()
                            .subtract(product.getPointDiscount() != null ? product.getPointDiscount() : BigDecimal.ZERO)
                            .subtract(product.getCouponDiscount() != null ? product.getCouponDiscount() : BigDecimal.ZERO);

                    return UserOrderProductCheckDto.builder()
                            .orderProductId(product.getOrderProductId())
                            .size(product.getSize())
                            .quantity(product.getQuantity())
                            .claimStatus(product.getClaimStatus())
                            .pointDiscount(product.getPointDiscount())
                            .payPrice(payPrice) // 계산된 값 설정
                            .productName(product.getProductName())
                            .productMainImage(product.getProductMainImage())
                            .build();
                }).collect(Collectors.toList());

        return OrderDetailsDto.builder()
                .orderId(order.getOrderId())
                .orderNumber(order.getOrderNumber())
                .orderDate(order.getOrderDate())
                .postalCode(order.getPostalCode())
                .recipientContact(order.getRecipientContact())
                .recipientName(order.getRecipientName())
                .address(order.getAddress())
                .detailedAddress(order.getDetailedAddress())
                .userOrderProductCheckDtos(userOrderProductCheckDtos)
                .build();
    }

    public OrderProductDto getOrderProductDetail(Long orderProductId) {
        OrderProductsVO product = orderProductsMapper.getOrderProductById(orderProductId);
        if (product == null) {
            throw new IllegalArgumentException("해당 주문 상품 정보가 존재하지 않습니다.");
        }
        return OrderProductDto.builder()
                .orderProductId(product.getOrderProductId())
                .size(product.getSize())
                .quantity(product.getQuantity())
                .claimStatus(product.getClaimStatus())
                .pointDiscount(product.getPointDiscount())
                .productPrice(product.getProductPrice()) // 계산된 값 설정
                .productName(product.getProductName())
                .productMainImage(product.getProductMainImage())
                .build();
    }


    @Transactional(rollbackFor = Exception.class)
    public String createNonUserOrder(NonUserOrderDto nonUserOrderDto) {

        // 주문 정보 생성
        OrdersVO ordersVO = OrdersVO.builder()
                .orderDate(new Date())
                .orderName(nonUserOrderDto.getOrderName())
                .orderContact(nonUserOrderDto.getOrderContact())
                .orderEmail(nonUserOrderDto.getOrderEmail())
                .recipientName(nonUserOrderDto.getRecipientName())
                .recipientContact(nonUserOrderDto.getRecipientContact())
                .postalCode(nonUserOrderDto.getPostalCode())
                .address(nonUserOrderDto.getAddress())
                .detailedAddress(nonUserOrderDto.getDetailedAddress())
                .orderNumber(OrderNumberGenerator.generateOrderNumber())
                .userId(null)
                .allPayment(nonUserOrderDto.getAllPayment())
                .build();

        // 주문 정보 DB 저장
        orderMapper.insertOrder(ordersVO);
        Long orderId = ordersVO.getOrderId();
        if (orderId == null) {
            throw new RuntimeException("주문 생성 실패: orderId가 NULL입니다.");
        }
        System.out.println("📌 생성된 주문 ID: " + orderId);

        // 주문 상품 정보 생성
        List<OrderProductsVO> orderProductsList = nonUserOrderDto.getOderProductsRequestDtos().stream().map(dto -> {
            // 상품 이미지 조회
            List<String> images = productImagesMapper.getImageUrlsById(dto.getProductColorId());
            if (images == null || images.isEmpty()) {
                throw new RuntimeException("해당 상품의 이미지가 없습니다. 상품 ID: " + dto.getProductColorId());
            }
            String mainImage = images.get(0);

            // 상품 정보 조회
            Long productId = productColorsMapper.getProductIdByColor(dto.getProductColorId());
            if (productId == null) {
                throw new RuntimeException("해당 색상에 대한 제품 ID를 찾을 수 없습니다. 상품 색상 ID: " + dto.getProductColorId());
            }

            ProductsVo productVO = productsMapper.getProductById(productId);
            if (productVO == null) {
                throw new RuntimeException("해당 상품 정보를 찾을 수 없습니다. 상품 ID: " + productId);
            }

            // 상품 색상 조회
            String color = productColorsMapper.getColorById(dto.getProductColorId());

            // 현재 재고 조회 (트랜잭션 내에서 Lock)
            int stock = sizesMapper.getStockByColorAndSize(dto.getProductColorId(), dto.getSize());
            if (stock - dto.getQuantity() < 0) {
                throw new RuntimeException("해당 상품은 현재 재고 부족으로 구매가 불가능합니다. 상품명: " + productVO.getProductName());
            }

            // 재고 차감
            sizesMapper.updateSizeByColorAndSize(dto.getProductColorId(), dto.getSize(), dto.getQuantity());

            return OrderProductsVO.builder()
                    .orderId(orderId)
                    .size(dto.getSize())
                    .quantity(dto.getQuantity())
                    .couponDiscount(dto.getCouponDiscount())
                    .buyConfirmDate(null)
                    .claimStatus("0")
                    .pointDiscount(dto.getPointDiscount())
                    .deliveryId(null)
                    .productPrice(dto.getProductPrice())
                    .productName(productVO.getProductName())
                    .color(color)
                    .deliveryCharge(dto.getDeliveryCharge())
                    .productMainImage(mainImage)
                    .build();
        }).toList();

        // 주문 상품 정보 DB 저장
        for (OrderProductsVO orderProduct : orderProductsList) {
            orderProductsMapper.insertOrderProduct(orderProduct);
        }
        return ordersVO.getOrderNumber();
    }

}

