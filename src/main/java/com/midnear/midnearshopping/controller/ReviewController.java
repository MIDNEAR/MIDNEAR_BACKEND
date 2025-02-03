package com.midnear.midnearshopping.controller;

import com.midnear.midnearshopping.domain.dto.review.CommentDto;
import com.midnear.midnearshopping.domain.dto.review.ProductReviewDto;
import com.midnear.midnearshopping.domain.dto.review.ReviewRequestDto;
import com.midnear.midnearshopping.domain.vo.users.CustomUserDetails;
import com.midnear.midnearshopping.exception.ApiResponse;
import com.midnear.midnearshopping.service.review.ReviewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final ReviewsService reviewsService;

    @PostMapping("/create")
    public ResponseEntity<?> createReview(@AuthenticationPrincipal CustomUserDetails customUserDetails, @ModelAttribute ReviewRequestDto reviewRequestDto) {
        try{
            reviewsService.createReview(customUserDetails.getUsername(), reviewRequestDto);
            return ResponseEntity.ok().body(new ApiResponse(true, "리뷰 작성 성공", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, "리뷰 작성 실패: "+ e.getMessage(), null));
        }
    }
    @PostMapping("/nonUserCreate")
    public ResponseEntity<?> nonUserCreateReview(@ModelAttribute ReviewRequestDto reviewRequestDto) {
        try{
            System.out.println("🔥 [Controller] /nonUserCreate 요청 도착!");
            reviewsService.nonUserCreateReview(reviewRequestDto);
            return ResponseEntity.ok().body(new ApiResponse(true, "리뷰 작성 성공", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, "리뷰 작성 실패: "+ e.getMessage(), null));
        }
    }


    @GetMapping("/byProduct")
    public ResponseEntity<ApiResponse> getProductReviews(
            @RequestParam String productName,
            @RequestParam(defaultValue = "1") int pageNumber) {

        ProductReviewDto reviews = reviewsService.getProductReviews(productName, pageNumber);
        return ResponseEntity.ok(new ApiResponse(true, "리뷰 조회 성공", reviews));
    }


    @PatchMapping("/makeInactive")
    public ResponseEntity<ApiResponse> updateReviewStatus(@RequestParam Long reviewId) {
        reviewsService.updateReviewStatus(reviewId);
        return ResponseEntity.ok(new ApiResponse(true, "리뷰 삭제 완료", null));
    }

    @GetMapping("/gathering")
    public ResponseEntity<ApiResponse> reviewImagesGathering(@RequestParam String productName,
                                                             @RequestParam(defaultValue = "1") int pageNumber) {
        List<String> response = reviewsService.reviewImageGathering(productName, pageNumber);
        return ResponseEntity.ok(new ApiResponse(true, "리뷰 사진 모아보기 조회 성공", response));
    }

    @PatchMapping("/comment")
    public ResponseEntity<ApiResponse> reviewImagesGathering(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                             @RequestBody CommentDto commentDto) {
        try{
        reviewsService.updateReviewComment(customUserDetails.getUsername(), commentDto);
        return ResponseEntity.ok(new ApiResponse(true, "리뷰 댓글작성 성공", null));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, e.getMessage(), null));
        }
    }
}
