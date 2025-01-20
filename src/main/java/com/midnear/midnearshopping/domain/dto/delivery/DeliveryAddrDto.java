package com.midnear.midnearshopping.domain.dto.delivery;

import com.midnear.midnearshopping.domain.vo.delivery.DeliveryAddressVO;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryAddrDto {
    private Integer deliveryAddressId;
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

        return DeliveryAddrDto.builder()
                .deliveryAddressId(vo.getDeliveryAddressId())
                .recipient(vo.getRecipient())
                .recipientContact(vo.getRecipientContact())
                .deliveryName(vo.getDeliveryName())
                .postalCode(vo.getPostalCode())
                .address(vo.getAddress())
                .detailAddress(vo.getDetailAddress())
                .defaultAddressStatus(vo.getDefaultAddressStatus())
                .deliveryRequest(vo.getDeliveryRequest())
                .userId(vo.getUserId())
                .build();
    }

}
