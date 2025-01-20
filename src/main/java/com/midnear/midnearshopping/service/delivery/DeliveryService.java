package com.midnear.midnearshopping.service.delivery;

import com.midnear.midnearshopping.domain.dto.delivery.DeliveryAddrDto;
import com.midnear.midnearshopping.domain.dto.delivery.UpdateDeliveryRequest;
import com.midnear.midnearshopping.domain.vo.delivery.DeliveryAddressVO;
import com.midnear.midnearshopping.mapper.delivery.DeliveryAddressMapper;
import com.midnear.midnearshopping.mapper.users.UsersMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryAddressMapper deliveryAddrMapper;
    private final UsersMapper usersmapper;

    @Transactional
    public void createDeliveryAddr(String id, DeliveryAddrDto deliveryAddrCreateDto) {
        DeliveryAddressVO deliveryAddressVO = DeliveryAddressVO.toEntity(deliveryAddrCreateDto);
        Integer userId = usersmapper.getUserIdById(id);
        if (userId == null) {
            throw new UsernameNotFoundException("존재하지 않는 유저입니다.");
        }
        deliveryAddressVO.setUserId(userId);
        deliveryAddrMapper.createDeliveryAddress(deliveryAddressVO);
    }
    //유저가 등록한 모든 주소 가져옴
    public List<DeliveryAddrDto> getAllDeliveryAddrs(String id) {
        Integer userId = usersmapper.getUserIdById(id);
        if (userId == null) {
            throw new UsernameNotFoundException("존재하지 않는 유저입니다.");
        }
        return deliveryAddrMapper.getDeliveryAddressesByUserId(userId);
    }
    public DeliveryAddrDto getDefaultAddress(String id) {
        Integer userId = usersmapper.getUserIdById(id);
        if (userId == null) {
            throw new UsernameNotFoundException("존재하지 않는 유저입니다.");
        }
        return deliveryAddrMapper.getDefaultAddr(userId);
    }
    public DeliveryAddrDto getDeliveryAddr(int deliveryId) {
        return deliveryAddrMapper.getDeliveryAddressById(deliveryId);
    }
    // 배송 주소 수정
    @Transactional
    public void updateDeliveryAddr(DeliveryAddrDto deliveryAddrUpdateDto) {
       DeliveryAddrDto updateAddr = deliveryAddrMapper.getDeliveryAddressById(deliveryAddrUpdateDto.getDeliveryAddressId());
       if(updateAddr == null) {
           throw new IllegalArgumentException("존재하지 않는 배송지입니다.");
       }
       deliveryAddrMapper.updateDeliveryAddress(DeliveryAddressVO.toEntity(deliveryAddrUpdateDto));
    }
    // 배송 메세지 수정
    @Transactional
    public void updateDeliveryRequest(UpdateDeliveryRequest updateDeliveryRequest) {
        DeliveryAddrDto updateAddr = deliveryAddrMapper.getDeliveryAddressById(updateDeliveryRequest.getDeliveryAddressId());
        if(updateAddr == null) {
            throw new IllegalArgumentException("존재하지 않는 배송지입니다.");
        }
        deliveryAddrMapper.updateDeliveryRequest(updateDeliveryRequest);
    }

    @Transactional
    public void deleteAddr(int deliveryId) {
        DeliveryAddrDto updateAddr = deliveryAddrMapper.getDeliveryAddressById(deliveryId);
        if(updateAddr == null) {
            throw new IllegalArgumentException("존재하지 않는 배송지입니다.");
        }
        deliveryAddrMapper.deleteDeliveryAddress(deliveryId);
    }
}
