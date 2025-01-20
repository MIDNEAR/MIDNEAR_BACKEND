package com.midnear.midnearshopping.controller;

import com.midnear.midnearshopping.domain.dto.delivery.DeliveryAddrDto;
import com.midnear.midnearshopping.domain.dto.delivery.UpdateDeliveryRequest;
import com.midnear.midnearshopping.domain.vo.users.CustomUserDetails;
import com.midnear.midnearshopping.exception.ApiResponse;
import com.midnear.midnearshopping.service.CustomUserDetailsService;
import com.midnear.midnearshopping.service.delivery.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/delivery")
public class DeliveryController {
    private final DeliveryService deliveryService;

    @PostMapping("/create-addr")
    public ResponseEntity<?> createAddr(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody DeliveryAddrDto dto) {
        try {
            deliveryService.createDeliveryAddr(customUserDetails.getUsername(), dto);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(true, "배송지 추가 성공", null));
        } catch (UsernameNotFoundException ex){
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false, ex.getMessage(), null));
        }
    }

    @GetMapping("/get-default-addr")
    public ResponseEntity<?> getDefaultAddr(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        try {
            DeliveryAddrDto response = deliveryService.getDefaultAddress(customUserDetails.getUsername());
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(true, "기본 배송지 조회 성공", response));
        } catch (UsernameNotFoundException ex){
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false, ex.getMessage(), null));
        }
    }

    @GetMapping("/get-addr")
    public ResponseEntity<?> getAddrById(@RequestParam Integer addrId) {
        try {
            DeliveryAddrDto response = deliveryService.getDeliveryAddr(addrId);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(true, "특정 배송지 조회 성공", response));
        } catch (UsernameNotFoundException ex){
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false, ex.getMessage(), null));
        }
    }
    @GetMapping("/get-addr-list")
    public ResponseEntity<?> getAddList(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        try {
            List<DeliveryAddrDto> response = deliveryService.getAllDeliveryAddrs(customUserDetails.getUsername());
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(true, "배송지 리스트 조회 성공", response));
        } catch (UsernameNotFoundException ex){
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false, ex.getMessage(), null));
        }
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateAddr(@RequestBody DeliveryAddrDto dto) {
        try {
            deliveryService.updateDeliveryAddr(dto);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(true, "배송지 수정 성공", null));
        } catch (IllegalArgumentException ex){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, ex.getMessage(), null));
        } catch (Exception ex){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, ex.getMessage(), null));
        }
    }

    @PatchMapping("/update-request")
    public ResponseEntity<?> updateDeliveryRequest(@RequestBody UpdateDeliveryRequest dto) {
        try {
            deliveryService.updateDeliveryRequest(dto);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(true, "배송 요청사항 수정 성공", null));
        } catch (IllegalArgumentException ex){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, ex.getMessage(), null));
        } catch (Exception ex){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, ex.getMessage(), null));
        }
    }
    @DeleteMapping
    public ResponseEntity<?> deleteAddr(@RequestParam Integer addrId) {
        try {
            deliveryService.deleteAddr(addrId);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(true, "배송지 삭제 완료", null));
        } catch (IllegalArgumentException ex){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, ex.getMessage(), null));
        } catch (Exception ex){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, ex.getMessage(), null));
        }
    }
}
