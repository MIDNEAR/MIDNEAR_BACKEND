package com.midnear.midnearshopping.mapper.coupon_point;

import com.midnear.midnearshopping.domain.dto.coupon_point.CouponInfoDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserCouponMapper {
    List<CouponInfoDto> getCouponsByUserId(Long userId);
    int getCouponsCountByUserId(Long userId);
    void changeStatus(Long userCouponId);
}
