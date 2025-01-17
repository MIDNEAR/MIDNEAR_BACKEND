package com.midnear.midnearshopping.mapper.delivery;

import com.midnear.midnearshopping.domain.vo.delivery.DeliveryAddressVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeliveryAddressMapper {
    void createDeliveryAddress(DeliveryAddressVO deliveryAddressVO);
    DeliveryAddressVO getDeliveryAddressById(Long deliveryAddressId);
}
