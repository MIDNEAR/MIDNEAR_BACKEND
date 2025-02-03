package com.midnear.midnearshopping.mapper.delivery;

import com.midnear.midnearshopping.domain.dto.delivery.DeliveryAddrDto;
import com.midnear.midnearshopping.domain.dto.delivery.UpdateDeliveryRequest;
import com.midnear.midnearshopping.domain.vo.delivery.DeliveryAddressVO;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface DeliveryAddressMapper {
    void createDeliveryAddress(DeliveryAddressVO deliveryAddressVO);
    DeliveryAddrDto getDeliveryAddressById(int deliveryAddressId);
    List<DeliveryAddrDto> getDeliveryAddressesByUserId(Long userId);
    DeliveryAddrDto getDefaultAddr(Long userId);
    void updateDeliveryAddress(DeliveryAddressVO deliveryAddressVO);
    void updateDeliveryRequest(UpdateDeliveryRequest updateDeliveryRequest);
    void deleteDeliveryAddress(int deliveryAddressId);
    BigDecimal getDeliveryCharge(String location);
}

