package com.midnear.midnearshopping.mapper.coupon_point;

import com.midnear.midnearshopping.domain.vo.coupon_point.ReviewPointVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReviewPointMapper {
    void setReviewPointAmount(ReviewPointVo reviewPointVo);
    void deletePreviousData();
    Long getPhotoReview();
    Long getTextReview();
}
