package com.midnear.midnearshopping.mapper.coupon_point;

import com.midnear.midnearshopping.domain.dto.coupon_point.UserPointDto;
import com.midnear.midnearshopping.domain.vo.coupon_point.PointVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PointMapper {
    void grantPoints(PointVo pointVo);
    List<UserPointDto> getPointList(Long userId);
    String getProductInfoByReviewId(Long reviewId);
}
