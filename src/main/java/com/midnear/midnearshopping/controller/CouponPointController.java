package com.midnear.midnearshopping.controller;

import com.midnear.midnearshopping.domain.dto.claim.CancelRequestDto;
import com.midnear.midnearshopping.domain.dto.coupon_point.PointDto;
import com.midnear.midnearshopping.domain.dto.coupon_point.PointToSelectedUserDto;
import com.midnear.midnearshopping.exception.ApiResponse;
import com.midnear.midnearshopping.service.CouponPointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CouponPointController {
    private final CouponPointService couponPointService;

    // 전체 포인트 지급
    @PostMapping("/point/grantAll")
    public ResponseEntity<ApiResponse> grantPointsToAll(@RequestBody PointDto pointDto) {
        try {
            couponPointService.grantPointsToAll(pointDto);
            return ResponseEntity.ok(new ApiResponse(true, "전체 포인트 지급 완료.", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 개별 포인트 지급
    @PostMapping("/point/grantSelectedUsers")
    public ResponseEntity<ApiResponse> grantPointsToSelectedUsers(@RequestBody PointToSelectedUserDto pointDto) {
        try {
            couponPointService.grantPointsToSelectedUsers(pointDto);
            return ResponseEntity.ok(new ApiResponse(true, "개별 포인트 지급 완료.", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }


}
