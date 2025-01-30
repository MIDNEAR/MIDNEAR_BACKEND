package com.midnear.midnearshopping.controller;

import com.midnear.midnearshopping.domain.dto.Inquiries.InquiriesDTO;
import com.midnear.midnearshopping.domain.dto.Inquiries.InquiryRequestDto;
import com.midnear.midnearshopping.domain.dto.Inquiries.UserInquiryListDto;
import com.midnear.midnearshopping.domain.vo.users.CustomUserDetails;
import com.midnear.midnearshopping.exception.ApiResponse;
import com.midnear.midnearshopping.service.inquirie.UserInquiryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userInquiries")
@RequiredArgsConstructor
@Slf4j
public class UserInquiryController {

    private final UserInquiryService userInquiryService;

    @PostMapping
    public ResponseEntity<ApiResponse> createInquiry(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody InquiryRequestDto requestDto) {
        try {
            userInquiryService.createInquiry(customUserDetails.getUsername(), requestDto);
            return ResponseEntity.ok(new ApiResponse(true, "문의글이 성공적으로 등록되었습니다.", null));
        } catch (UsernameNotFoundException e) {
            log.error("문의글 작성 실패 - 유저 없음: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "존재하지 않는 유저입니다.", null));
        } catch (Exception e) {
            log.error("문의글 작성 실패: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "문의글 작성 중 오류가 발생했습니다."+e.getMessage(), null));
        }
    }
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getList(@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber) {
        try {
            List<UserInquiryListDto> response = userInquiryService.selectInquirylist(pageNumber);
            return ResponseEntity.ok(new ApiResponse(true, "문의글 리스트 성공적 조회", response));

        } catch (Exception e) {
            log.error("문의글 작성 실패: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "문의글 리스트 조회 중 오류가 발생했습니다."+ e.getMessage(), null));
        }
    }
    @GetMapping("/getInquiry")
    public ResponseEntity<ApiResponse> getInquiry(@RequestParam Long inquiryId) {
        try {
            InquiriesDTO response = userInquiryService.selectInquiry(inquiryId);
            return ResponseEntity.ok(new ApiResponse(true, "문의글 성공적 조회", response));
        } catch (Exception e) {
            log.error("문의글 작성 실패: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "문의글 조회 중 오류가 발생했습니다." + e.getMessage(), null));
        }
    }
}
