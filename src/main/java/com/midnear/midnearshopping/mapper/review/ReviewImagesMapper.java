package com.midnear.midnearshopping.mapper.review;

import com.midnear.midnearshopping.domain.vo.review.ReviewImagesVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ReviewImagesMapper {
    void insertReviewImage(ReviewImagesVO reviewImagesVO);
    List<ReviewImagesVO> getImagesByReviewId(@Param("reviewId") Long reviewId);
    void deleteReviewImagesByReviewId(@Param("reviewId") Long reviewId);

    List<String> getReviewImagesByProduct(
            @Param("productName") String productName,
            @Param("pageSize") int pageSize,
            @Param("offset") int offset
    );
}