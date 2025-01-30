package com.midnear.midnearshopping.controller;

import com.midnear.midnearshopping.domain.dto.claim.CancelRequestDto;
import com.midnear.midnearshopping.domain.dto.coupon_point.PointDto;
import com.midnear.midnearshopping.domain.dto.coupon_point.PointToSelectedUserDto;
import com.midnear.midnearshopping.domain.dto.coupon_point.SearchUserDto;
import com.midnear.midnearshopping.exception.ApiResponse;
import com.midnear.midnearshopping.service.CouponPointService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    // 사용자 id로 검색
    @GetMapping("/point/searchUser")
    public ResponseEntity<ApiResponse> searchUser(@RequestParam("id") String id, @RequestParam("pageNumber")int pageNumber) {
        try {
            Map<String, Object> response = couponPointService.searchUser(id, pageNumber);
            return ResponseEntity.ok(new ApiResponse(true, "사용자 검색 완료", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }


}
