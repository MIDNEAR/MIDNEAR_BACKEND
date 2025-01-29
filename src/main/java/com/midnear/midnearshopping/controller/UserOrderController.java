package com.midnear.midnearshopping.controller;

import com.midnear.midnearshopping.domain.dto.order.*;
import com.midnear.midnearshopping.domain.vo.users.CustomUserDetails;
import com.midnear.midnearshopping.exception.ApiResponse;
import com.midnear.midnearshopping.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class UserOrderController {
    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createOrder(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody UserOrderDto userOrderDto) {
        try {
            orderService.createOrder(customUserDetails.getUsername(), userOrderDto);
            return ResponseEntity.ok(new ApiResponse(true, "주문이 성공적으로 생성되었습니다.", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "주문 생성 중 오류 발생: " + e.getMessage(), null));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getOrderDetails(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestParam int pageNumber, @RequestParam(defaultValue = "latest") String sort) {
        try {
            List<UserOrderCheckDto> orderDetails = orderService.getOrders(customUserDetails.getUsername(), pageNumber, sort);
            return ResponseEntity.ok(new ApiResponse(true, "주문 조회 성공", orderDetails));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse(false, "주문 조회 중 오류 발생: " + e.getMessage(), null));
        }
    }
    @GetMapping("/detail")
    public ResponseEntity<ApiResponse> getOrderDetails(@RequestParam Long orderId) {
        try {
            OrderDetailsDto orderDetails = orderService.getOrderDetails(orderId);
            return ResponseEntity.ok(new ApiResponse(true, "주문 조회 성공", orderDetails));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse(false, "주문 조회 중 오류 발생: " + e.getMessage(), null));
        }
    }
    //취소 페이지에서 필요한 api.....
    @GetMapping("/forCancel")
    public ResponseEntity<ApiResponse> getOrderProduct(@RequestParam Long orderProductId) {
        try {
            OrderProductDto orderProduct = orderService.getOrderProductDetail(orderProductId);
            return ResponseEntity.ok(new ApiResponse(true, "주문 조회 성공", orderProduct));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse(false, "주문 조회 중 오류 발생: " + e.getMessage(), null));
        }
    }

    @PostMapping("/nonUserCreate")
    public ResponseEntity<ApiResponse> createNonUserOrder(@RequestBody NonUserOrderDto nonUserOrderDto) {
        try {
            String orderNumber = orderService.createNonUserOrder(nonUserOrderDto);
            return ResponseEntity.ok(new ApiResponse(true, "비회원 주문이 성공적으로 생성되었습니다.", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "주문 생성 중 오류 발생: " + e.getMessage(), null));
        }
    }

}
