package com.midnear.midnearshopping.controller;

import com.midnear.midnearshopping.domain.dto.cart.CartProductDto;
import com.midnear.midnearshopping.domain.vo.users.CustomUserDetails;
import com.midnear.midnearshopping.exception.ApiResponse;
import com.midnear.midnearshopping.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    /**
     * 장바구니 생성
     */
    @PostMapping("/create")
    public ResponseEntity<?> createCart(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        try {
            String id = customUserDetails.getUsername();
            cartService.makeCart(id);
            return ResponseEntity.ok(new ApiResponse(true, "장바구니가 생성되었습니다.", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    /**
     * 장바구니에 상품 추가
     */
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam Long productColorId,
            @RequestParam int quantity,
            @RequestParam String size
    ) {
        try {
            String id = customUserDetails.getUsername();
            cartService.addCart(id, productColorId, quantity, size);
            return ResponseEntity.ok(new ApiResponse(true, "상품이 장바구니에 추가되었습니다.", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    /**
     * 장바구니에서 상품 삭제
     */
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteFromCart(@RequestParam Long cartProductId) {
        try {
            cartService.deleteFromCart(cartProductId);
            return ResponseEntity.ok(new ApiResponse(true, "장바구니에서 상품이 삭제되었습니다.", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    /**
     * 장바구니 조회
     */
    @GetMapping
    public ResponseEntity<ApiResponse> getCart(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        try {
            String id = customUserDetails.getUsername();
            List<CartProductDto> cartProducts = cartService.getCart(id);
            return ResponseEntity.ok(new ApiResponse(true, "장바구니 조회 성공", cartProducts));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage(), null));
        }
    }
}
