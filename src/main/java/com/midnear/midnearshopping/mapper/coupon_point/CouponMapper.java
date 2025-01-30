package com.midnear.midnearshopping.mapper.coupon_point;

import com.midnear.midnearshopping.domain.vo.coupon_point.CouponVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CouponMapper {
    void registerCoupon(CouponVo couponVo);
    void grantCoupon(@Param("userId") Long userId, @Param("couponId") Long couponId);
}
