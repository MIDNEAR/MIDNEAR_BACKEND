package com.midnear.midnearshopping.mapper.ShippingReturns;

import com.midnear.midnearshopping.domain.vo.shipping_returns.ShippingReturnsVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShippingReturnsMapper {

    ShippingReturnsVo getShippingReturns();

    void updateShippingReturns(ShippingReturnsVo shippingReturnsVo);

    void updateShippingPolicy(ShippingReturnsVo shippingReturnsVo);
}
