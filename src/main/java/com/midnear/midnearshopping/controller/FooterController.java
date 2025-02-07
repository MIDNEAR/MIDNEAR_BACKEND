package com.midnear.midnearshopping.controller;

import com.midnear.midnearshopping.domain.dto.Inquiries.InquiriesDTO;
import com.midnear.midnearshopping.exception.ApiResponse;
import com.midnear.midnearshopping.service.productManagement.FooterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/footer")
@Slf4j
public class FooterController {
    private final FooterService footerService;

    @PutMapping("/updateFooter")
    public ResponseEntity<ApiResponse> updateFooter(String footerContents){
        try {
            footerService.updateFooter(footerContents);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "footer 수정 성공.", null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }

    }
}
