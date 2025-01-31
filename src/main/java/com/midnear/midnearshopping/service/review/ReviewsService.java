package com.midnear.midnearshopping.service.review;

import com.midnear.midnearshopping.domain.dto.FileDto;
import com.midnear.midnearshopping.domain.dto.review.ProductReviewDto;
import com.midnear.midnearshopping.domain.dto.review.ReviewListDto;
import com.midnear.midnearshopping.domain.dto.review.ReviewRequestDto;
import com.midnear.midnearshopping.domain.vo.magazines.MagazineImagesVO;
import com.midnear.midnearshopping.domain.vo.magazines.MagazinesVO;
import com.midnear.midnearshopping.domain.vo.review.ReviewImagesVO;
import com.midnear.midnearshopping.domain.vo.review.ReviewsVO;
import com.midnear.midnearshopping.mapper.review.ReviewImagesMapper;
import com.midnear.midnearshopping.mapper.review.ReviewsMapper;
import com.midnear.midnearshopping.mapper.users.UsersMapper;
import com.midnear.midnearshopping.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewsService {
    private final ReviewsMapper reviewsMapper;
    private final ReviewImagesMapper reviewImagesMapper;
    private final UsersMapper usersMapper;
    private final S3Service s3Service;
    private static final int pageSize = 2;

    @Transactional
    public void createReview(String id, ReviewRequestDto reviewRequestDto) {
        Integer userId = usersMapper.getUserIdById(id);
        if(userId == null){
            throw new RuntimeException("존재하지 않는 유저입니다");
        }
        ReviewsVO reviewsVO = ReviewsVO.builder()
                .createdAt(new Date())
                .modifiedDate(null)
                .rating(reviewRequestDto.getRating())
                .review(reviewRequestDto.getReview())
                .reviewStatus("active")
                .userId(userId)
                .orderProductId(reviewRequestDto.getOrderProductId())
                .build();
        reviewsMapper.insertReview(reviewsVO);
        Long reviewId = reviewsVO.getReviewId();
        List<FileDto> fileInfo;
        try {
            fileInfo = s3Service.uploadFiles("review", reviewRequestDto.getFiles());
        } catch (Exception e) {
            throw new RuntimeException("파일 업로드 중 오류가 발생했습니다.", e);
        }
        try {
            for (FileDto file : fileInfo) {
                ReviewImagesVO imagesVo = ReviewImagesVO.builder()
                        .reviewImageUrl(file.getFileUrl())
                        .fileSize(file.getFileSize())
                        .extension(file.getExtension())
                        .reviewId(reviewId)
                        .build();
                reviewImagesMapper.insertReviewImage(imagesVo);
            }

        } catch (Exception e) {
            // S3에 이미 업로드된 파일 삭제
            for (FileDto file : fileInfo) {
                s3Service.deleteFile(file.getFileUrl());
            }
            e.printStackTrace();
            throw new RuntimeException("이미지 정보를 데이터베이스에 저장하는 중 오류가 발생했습니다.", e);
        }

    }
    public void updateReviewStatus(Long reviewId) {
        reviewsMapper.updateReviewStatus(reviewId);
    }

    public void updateReviewComment(String id, Long reviewId) {
        Integer userId = usersMapper.getUserIdById(id);
        if(userId == null){
            throw new RuntimeException("존재하지 않는 유저입니다");
        }
        if(userId !=1){
            throw new RuntimeException("관리자만 리뷰에 댓글을 달 수 있습니다");
        }
        reviewsMapper.updateReviewComment(reviewId);
    }

    public ProductReviewDto getProductReviews(String productName, int pageNumber) {
        int offset = (pageNumber - 1) * pageSize;
        ProductReviewDto dto = new ProductReviewDto();

        dto.setImageReviewCount(reviewsMapper.getImageReviewCount(productName));
        dto.setReviewCount(reviewsMapper.getReviewCount(productName));
        dto.setAllReviewImages(reviewsMapper.getAllReviewImages(productName));
        dto.setReviewList(reviewsMapper.getReviewList(productName, offset, pageSize));


        return dto;
    }

    public List<String> reviewImageGathering(String productName, int pageNumber){
        int offset = (pageNumber - 1) * pageSize;
        return reviewImagesMapper.getReviewImagesByProduct(productName, offset, pageSize);
    }

}
