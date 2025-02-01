package com.midnear.midnearshopping.controller;

import com.midnear.midnearshopping.domain.dto.coupon_point.CouponInfoDto;
import com.midnear.midnearshopping.domain.dto.coupon_point.UserPointDto;
import com.midnear.midnearshopping.domain.vo.users.CustomUserDetails;
import com.midnear.midnearshopping.exception.ApiResponse;
import com.midnear.midnearshopping.service.CouponPointService;

import lombok.RequiredArgsConstructor;
import org.apache.poi.hpsf.Decimal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userCouponPoint")
@RequiredArgsConstructor
public class UserCouponPointController {
    private final CouponPointService couponPointService;

    // 사용자 쿠폰 리스트 조회
    @GetMapping("/coupons")
    public ResponseEntity<ApiResponse> getCouponsByUserId(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        List<CouponInfoDto> coupons = couponPointService.getCouponsByUserId(customUserDetails.getUsername());
        return ResponseEntity.ok(new ApiResponse(true, "Coupons retrieved successfully", coupons));
    }

    // 사용자 쿠폰 총 개수 조회
    @GetMapping("/coupons/count")
    public ResponseEntity<ApiResponse> getCouponsCountByUserId(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        int count = couponPointService.getCouponsCountByUserId(customUserDetails.getUsername());
        return ResponseEntity.ok(new ApiResponse(true, "Coupon count retrieved successfully", count));
    }

    // 사용자 포인트 사용 내역 조회
    @GetMapping("/points")
    public ResponseEntity<ApiResponse> getPointLists(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        List<UserPointDto> points = couponPointService.getPointLists(customUserDetails.getUsername());
        return ResponseEntity.ok(new ApiResponse(true, "Point history retrieved successfully", points));
    }

    // 사용자 포인트 총량 조회
    @GetMapping("/points/total")
    public ResponseEntity<ApiResponse> getPoints(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Decimal points = couponPointService.getPoints(customUserDetails.getUsername());
        return ResponseEntity.ok(new ApiResponse(true, "Total points retrieved successfully", points));
    }
}