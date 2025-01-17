package com.midnear.midnearshopping.service.delivery;

import com.midnear.midnearshopping.domain.dto.delivery.DeliveryAddrDto;
import com.midnear.midnearshopping.domain.vo.delivery.DeliveryAddressVO;
import com.midnear.midnearshopping.mapper.delivery.DeliveryAddressMapper;
import com.midnear.midnearshopping.mapper.users.UsersMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryAddressMapper deliveryAddrMapper;
    private final UsersMapper usersmapper;

    @Transactional
    public void createDeliveryAddr(String id, DeliveryAddrDto deliveryAddrCreateDto) {
        DeliveryAddressVO deliveryAddressVO = DeliveryAddressVO.toEntity(deliveryAddrCreateDto);
        deliveryAddressVO.setUserId(usersmapper.getUserIdById(id));
        deliveryAddrMapper.createDeliveryAddress(deliveryAddressVO);

    }

}
