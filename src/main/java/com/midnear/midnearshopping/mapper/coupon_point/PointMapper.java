package com.midnear.midnearshopping.mapper.coupon_point;

import com.midnear.midnearshopping.domain.vo.coupon_point.PointVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PointMapper {
    void grantPoints(PointVo pointVo);
}
