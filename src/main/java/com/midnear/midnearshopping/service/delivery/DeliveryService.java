package com.midnear.midnearshopping.service.delivery;

import com.midnear.midnearshopping.domain.dto.delivery.DeliveryAddrDto;
import com.midnear.midnearshopping.domain.dto.delivery.UpdateDeliveryRequest;
import com.midnear.midnearshopping.domain.vo.delivery.DeliveryAddressVO;
import com.midnear.midnearshopping.mapper.delivery.DeliveryAddressMapper;
import com.midnear.midnearshopping.mapper.delivery.DeliveryInfoMapper;
import com.midnear.midnearshopping.mapper.users.UsersMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static io.lettuce.core.KillArgs.Builder.id;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryAddressMapper deliveryAddrMapper;
    private final UsersMapper usersmapper;
    private final DeliveryInfoMapper deliveryInfoMapper;

    @Transactional
    public void createDeliveryAddr(String id, DeliveryAddrDto deliveryAddrCreateDto) {
        DeliveryAddressVO deliveryAddressVO = DeliveryAddressVO.toEntity(deliveryAddrCreateDto);
        Long userId = usersmapper.getUserIdById(id);
        if (userId == null) {
            throw new UsernameNotFoundException("존재하지 않는 유저입니다.");
        }
        deliveryAddressVO.setUserId(userId);
        if(deliveryAddrMapper.countAddr(userId)==0 && deliveryAddressVO.getDefaultAddressStatus()==0){
            deliveryAddressVO.setDefaultAddressStatus(1);
        }
        deliveryAddrMapper.createDeliveryAddress(deliveryAddressVO);
    }
    //유저가 등록한 모든 주소 가져옴
    public List<DeliveryAddrDto> getAllDeliveryAddrs(String id) {
        Long userId = usersmapper.getUserIdById(id);
        if (userId == null) {
            throw new UsernameNotFoundException("존재하지 않는 유저입니다.");
        }
        return deliveryAddrMapper.getDeliveryAddressesByUserId(userId);
    }
    public DeliveryAddrDto getDefaultAddress(String id) {
        Long userId = usersmapper.getUserIdById(id);
        if (userId == null) {
            throw new UsernameNotFoundException("존재하지 않는 유저입니다.");
        }
        return deliveryAddrMapper.getDefaultAddr(userId);
    }



    public void updateDefault(int deliveryId) {
        DeliveryAddrDto addr = deliveryAddrMapper.getDeliveryAddressById(deliveryId);
        Integer oldDefault = deliveryAddrMapper.getDefaultAddrId(addr.getUserId());
        deliveryAddrMapper.updateDefault(deliveryId, 1);
        deliveryAddrMapper.updateDefault(oldDefault, 0);
    }





    public DeliveryAddrDto getDeliveryAddr(int deliveryId) {
        DeliveryAddrDto addr = deliveryAddrMapper.getDeliveryAddressById(deliveryId);
        if(addr == null) {
            throw new IllegalArgumentException("존재하지 않는 배송지입니다.");
        }
        return addr;
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

    public String getInvoiceNumber(Long deliveryId){
        return deliveryInfoMapper.getInvoiceNumberById(deliveryId);
    }
}
