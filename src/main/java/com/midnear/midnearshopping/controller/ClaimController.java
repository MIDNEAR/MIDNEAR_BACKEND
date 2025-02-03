package com.midnear.midnearshopping.controller;

import com.midnear.midnearshopping.domain.dto.claim.CancelRequestDto;
import com.midnear.midnearshopping.domain.dto.claim.ExchangeRequestDto;
import com.midnear.midnearshopping.domain.dto.claim.ReturnRequestDto;
import com.midnear.midnearshopping.domain.dto.order.UserOrderCheckDto;
import com.midnear.midnearshopping.domain.vo.users.CustomUserDetails;
import com.midnear.midnearshopping.exception.ApiResponse;
import com.midnear.midnearshopping.service.claim.ClaimService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/claim")
@RequiredArgsConstructor
@Slf4j
public class ClaimController {

    private final ClaimService claimService;

    @PostMapping("/cancel")
    public ResponseEntity<ApiResponse> createCancel(@RequestBody CancelRequestDto cancelRequestDto) {
        try {
            claimService.createCancel(cancelRequestDto);
            return ResponseEntity.ok(new ApiResponse(true, "취소 요청이 성공적으로 접수되었습니다.", null));
        } catch (Exception e) {
            log.error("취소 요청 처리 중 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "취소 요청 처리 중 오류가 발생했습니다.", null));
        }
    }

    @PostMapping("/exchange")
    public ResponseEntity<ApiResponse> createExchange(@RequestBody ExchangeRequestDto exchangeRequestDto) {
        try {
            claimService.createExchange(exchangeRequestDto);
            return ResponseEntity.ok(new ApiResponse(true, "교환 요청이 성공적으로 접수되었습니다.", null));
        } catch (Exception e) {
            log.error("교환 요청 처리 중 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "교환 요청 처리 중 오류가 발생했습니다.", null));
        }
    }

    @PostMapping("/return")
    public ResponseEntity<ApiResponse> createReturn(@RequestBody ReturnRequestDto returnRequestDto) {
        try {
            claimService.createReturns(returnRequestDto);
            return ResponseEntity.ok(new ApiResponse(true, "반품 요청이 성공적으로 접수되었습니다.", null));
        } catch (Exception e) {
            log.error("반품 요청 처리 중 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "반품 요청 처리 중 오류가 발생했습니다.", null));
        }
    }
    @GetMapping("/filtering")
    public ResponseEntity<ApiResponse> getClaimOrders(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam int pageNumber,
            @RequestParam(required = false, defaultValue = "date") String sort,
            @RequestParam(required = false, defaultValue = "all") String filter
    ) {
        try {
            List<UserOrderCheckDto> orders =claimService.getClaimOrders(customUserDetails.getUsername(), pageNumber, sort, filter);
            return ResponseEntity.ok(new ApiResponse(true, "주문 정보 조회 성공", orders));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }
}
