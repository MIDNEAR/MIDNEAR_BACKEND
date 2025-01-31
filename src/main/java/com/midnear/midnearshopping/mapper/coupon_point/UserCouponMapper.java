package com.midnear.midnearshopping.mapper.coupon_point;

import com.midnear.midnearshopping.domain.dto.coupon_point.CouponInfoDto;
import com.midnear.midnearshopping.domain.vo.coupon_point.UserCouponVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserCouponMapper {
    List<CouponInfoDto> getCouponsByUserId(Integer userId);
    int getCouponsCountByUserId(Integer userId);
    void changeStatus(Long userCouponId);
}
