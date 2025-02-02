package com.midnear.midnearshopping.controller;

import com.midnear.midnearshopping.domain.dto.coupon_point.CouponDto;
import com.midnear.midnearshopping.domain.dto.coupon_point.CouponToSelectedUserDto;
import com.midnear.midnearshopping.domain.dto.coupon_point.PointDto;
import com.midnear.midnearshopping.domain.dto.coupon_point.PointToSelectedUserDto;
import com.midnear.midnearshopping.domain.vo.coupon_point.CouponVo;
import com.midnear.midnearshopping.domain.vo.coupon_point.ReviewPointVo;
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

    // 사용자 id로 검색(개별 쿠폰, 포인트 지급에서 사용)
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

    // 리뷰 포인트 금액 설정
    @PostMapping("/point/setReviewPointAmount")
    public ResponseEntity<ApiResponse> setReviewPointAmount(@RequestBody ReviewPointVo reviewPointVo) {
        try {
            couponPointService.setReviewPointAmount(reviewPointVo);
            return ResponseEntity.ok(new ApiResponse(true, "리뷰 포인트 금액 변경 완료.", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 전체 쿠폰 지급
    @PostMapping("/coupon/grantAll")
    public ResponseEntity<ApiResponse> grantCouponToAll(@RequestBody CouponDto couponDto) {
        try {
            couponPointService.grantCouponToAll(couponDto);
            return ResponseEntity.ok(new ApiResponse(true, "전체 쿠폰 지급 완료.", null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "할인 퍼센트가 올바르지 않습니다.", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 개별 쿠폰 지급
    @PostMapping("/coupon/grantSelectedUsers")
    public ResponseEntity<ApiResponse> grantCouponToSelectedUsers(@RequestBody CouponToSelectedUserDto couponDto) {
        try {
            couponPointService.grantCouponToSelectedUsers(couponDto);
            return ResponseEntity.ok(new ApiResponse(true, "개별 쿠폰 지급 완료.", null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "할인 퍼센트가 올바르지 않습니다.", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 리뷰 승인 시 포인트 지급
    @PostMapping("/point/grantReviewPoint")
    public ResponseEntity<ApiResponse> grantReviewPoint(@RequestParam("reviewId") Long reviewId) {
        try {
            couponPointService.grantReviewPoint(reviewId);
            return ResponseEntity.ok(new ApiResponse(true, "리뷰 포인트 지급 완료.", null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

}
