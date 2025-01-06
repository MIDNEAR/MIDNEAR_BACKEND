package com.midnear.midnearshopping.controller;

import com.midnear.midnearshopping.domain.dto.users.SmsRequestDto;
import com.midnear.midnearshopping.domain.dto.users.SmsVerifyDto;
import com.midnear.midnearshopping.exception.ApiResponse;
import com.midnear.midnearshopping.service.SmsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.IgnoreForbiddenApisErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sms")
@RequiredArgsConstructor
public class SmsController {

    private final SmsService smsService;

    @PostMapping("/send")
    public ResponseEntity<?> SendSMS(@RequestBody @Valid SmsRequestDto smsRequestDto){
        try{
            smsService.sendSms(smsRequestDto);
            return ResponseEntity.ok(new ApiResponse(true, "인증 문자 전송 완료", null));
        } catch (IllegalArgumentException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, ex.getMessage(), null));
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyCode(@RequestBody @Valid SmsVerifyDto smsVerifyDto){
        boolean verify = smsService.verifyCode(smsVerifyDto);
        if (verify) {
            return ResponseEntity.ok(new ApiResponse(true, "인증에 성공했습니다.", null));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, "인증에 실패했습니다.", null));
        }
    }
}
