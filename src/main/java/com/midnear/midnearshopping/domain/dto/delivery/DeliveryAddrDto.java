package com.midnear.midnearshopping.domain.dto.delivery;

import com.midnear.midnearshopping.domain.vo.delivery.DeliveryAddressVO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryAddrDto {
    private int deliveryAddressId;
    private String recipient;
    private String recipientContact;
    private String deliveryName;
    private String postalCode;
    private String address;
    private String detailAddress;
    private Integer defaultAddressStatus; //기본 배송지 여부?
    private String deliveryRequest; //배송 요청사할
    private int userId;

    public static DeliveryAddrDto toDto(DeliveryAddressVO vo) {
        if (vo == null) {
            return null;
        }

        DeliveryAddrDto dto = new DeliveryAddrDto();
        dto.setDeliveryAddressId(vo.getDeliveryAddressId());
        dto.setRecipient(vo.getRecipient());
        dto.setRecipientContact(vo.getRecipientContact());
        dto.setDeliveryName(vo.getDeliveryName());
        dto.setPostalCode(vo.getPostalCode());
        dto.setAddress(vo.getAddress());
        dto.setDetailAddress(vo.getDetailAddress());
        dto.setDefaultAddressStatus(vo.getDefaultAddressStatus());
        dto.setDeliveryRequest(vo.getDeliveryRequest());
        dto.setUserId(vo.getUserId());

        return dto;
    }
}
