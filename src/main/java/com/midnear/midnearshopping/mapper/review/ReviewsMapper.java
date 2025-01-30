package com.midnear.midnearshopping.mapper.review;

import com.midnear.midnearshopping.domain.dto.review.ProductReviewDto;
import com.midnear.midnearshopping.domain.dto.review.ReviewListDto;
import com.midnear.midnearshopping.domain.vo.review.ReviewsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReviewsMapper {
    void insertReview(ReviewsVO reviewsVO);
    void updateReview(ReviewsVO reviewsVO);
    void updateReviewStatus(Long reviewId);
    int getImageReviewCount(@Param("productName") String productName);
    int getReviewCount(@Param("productName") String productName);
    List<String> getAllReviewImages(@Param("productName") String productName);
    List<ReviewListDto> getReviewList(@Param("productName") String productName, @Param("offset") int offset, @Param("pageSize") int pageSize);
    List<String> getReviewImages(@Param("reviewId") Long reviewId);
    void updateReviewComment(Long reviewId);
}