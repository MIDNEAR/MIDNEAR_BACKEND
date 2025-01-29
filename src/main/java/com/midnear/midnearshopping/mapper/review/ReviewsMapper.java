package com.midnear.midnearshopping.mapper.review;

import com.midnear.midnearshopping.domain.vo.review.ReviewsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReviewsMapper {
    void insertReview(ReviewsVO reviewsVO);
    ReviewsVO getReviewById(@Param("reviewId") Long reviewId);
    List<ReviewsVO> getReviewsByProductId(@Param("userProductId") Long userProductId);
    void updateReview(ReviewsVO reviewsVO);
    void deleteReview(@Param("reviewId") Long reviewId);
}