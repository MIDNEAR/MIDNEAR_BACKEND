package com.midnear.midnearshopping.mapper.delivery;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeliveryInfoMapper {
    String getInvoiceNumberById(Long deliveryId);
}
