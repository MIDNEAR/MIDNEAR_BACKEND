package com.midnear.midnearshopping.controller;

import com.midnear.midnearshopping.domain.dto.review.ReviewRequestDto;
import com.midnear.midnearshopping.domain.vo.users.CustomUserDetails;
import com.midnear.midnearshopping.exception.ApiResponse;
import com.midnear.midnearshopping.service.review.ReviewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final ReviewsService reviewsService;

    @PostMapping("/create")
    public ResponseEntity<?> createReview(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody ReviewRequestDto reviewRequestDto) {
        try{
            reviewsService.createReview(customUserDetails.getUsername(), reviewRequestDto);
            return ResponseEntity.ok().body(new ApiResponse(true, "리뷰 작성 성공", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, "리뷰 작성 실패: "+ e.getMessage(), null));
        }
    }
}
