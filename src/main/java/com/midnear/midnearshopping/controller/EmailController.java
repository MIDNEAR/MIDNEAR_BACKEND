package com.midnear.midnearshopping.controller;

import com.midnear.midnearshopping.domain.dto.users.EmailRequestDto;
import com.midnear.midnearshopping.domain.dto.users.EmailVerifyDto;
import com.midnear.midnearshopping.domain.dto.users.SmsRequestDto;
import com.midnear.midnearshopping.domain.dto.users.SmsVerifyDto;
import com.midnear.midnearshopping.exception.ApiResponse;
import com.midnear.midnearshopping.service.MailSendService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {

    private final MailSendService mailSendService;

    @PostMapping("/send")
    public ResponseEntity<?> SendEmail(@RequestBody @Valid EmailRequestDto emailRequestDto){
        try{
            mailSendService.sendEmail(emailRequestDto);
            return ResponseEntity.ok(new ApiResponse(true, "인증 이메일 전송 완료", null));
        } catch (MessagingException ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, "인증 메일 전송 중 오류가 발생했습니다. 다시 시도해주세요.", null));
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyEmail(@RequestBody @Valid EmailVerifyDto emailVerifyDto){
        boolean verify = mailSendService.verifyCode(emailVerifyDto);
        if (verify) {
            return ResponseEntity.ok(new ApiResponse(true, "인증에 성공했습니다.", null));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, "인증에 실패했습니다.", null));
        }
    }



}
