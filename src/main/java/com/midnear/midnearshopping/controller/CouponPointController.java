package com.midnear.midnearshopping.controller;

import com.midnear.midnearshopping.domain.dto.coupon_point.CouponDto;
import com.midnear.midnearshopping.domain.dto.coupon_point.CouponToSelectedUserDto;
import com.midnear.midnearshopping.domain.dto.coupon_point.PointDto;
import com.midnear.midnearshopping.domain.dto.coupon_point.PointToSelectedUserDto;
import com.midnear.midnearshopping.domain.vo.coupon_point.ReviewPointVo;
import com.midnear.midnearshopping.exception.ApiResponse;
import com.midnear.midnearshopping.service.CouponPointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CouponPointController {
    private final CouponPointService couponPointService;

    private boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(authentication.getName());
        return authentication != null && "admin".equals(authentication.getName());
    }

    // 전체 포인트 지급
    @PostMapping("/point/grantAll")
    public ResponseEntity<ApiResponse> grantPointsToAll(@RequestBody PointDto pointDto) {
        try {
            if (!isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
            }
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
            if (!isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
            }
            couponPointService.grantPointsToSelectedUsers(pointDto);
            return ResponseEntity.ok(new ApiResponse(true, "개별 포인트 지급 완료.", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 사용자 id로 검색(개별 쿠폰, 포인트 지급에서 사용)
    @GetMapping("/point/searchUser")
    public ResponseEntity<ApiResponse> searchUser(@RequestParam("id") String id, @RequestParam(value = "pageNumber", defaultValue = "1")int pageNumber) {
        try {
            if (!isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
            }
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
            if (!isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
            }
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
            if (!isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
            }
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
            if (!isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
            }
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
            if (!isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
            }
            couponPointService.grantReviewPoint(reviewId);
            return ResponseEntity.ok(new ApiResponse(true, "리뷰 포인트 지급 완료.", null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

}
