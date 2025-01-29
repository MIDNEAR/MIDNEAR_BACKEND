package com.midnear.midnearshopping.controller;

import com.midnear.midnearshopping.domain.dto.order.UserOrderDto;
import com.midnear.midnearshopping.domain.vo.users.CustomUserDetails;
import com.midnear.midnearshopping.exception.ApiResponse;
import com.midnear.midnearshopping.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
}
