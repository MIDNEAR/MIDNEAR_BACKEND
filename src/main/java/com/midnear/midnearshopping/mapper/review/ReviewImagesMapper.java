package com.midnear.midnearshopping.mapper.review;

import com.midnear.midnearshopping.domain.vo.review.ReviewImagesVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ReviewImagesMapper {
    void insertReviewImage(ReviewImagesVO reviewImagesVO);
    List<String> getReviewImagesByProduct(
            @Param("productName") String productName,
            @Param("offset") int offset,
            @Param("pageSize") int pageSize
    );
}