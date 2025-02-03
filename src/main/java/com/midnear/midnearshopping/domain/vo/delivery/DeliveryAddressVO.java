package com.midnear.midnearshopping.domain.vo.delivery;

import com.midnear.midnearshopping.domain.dto.delivery.DeliveryAddrDto;
import com.midnear.midnearshopping.domain.dto.users.UsersDto;
import com.midnear.midnearshopping.domain.vo.users.UsersVO;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DeliveryAddressVO {
    private int deliveryAddressId;
    private String deliveryName;
    private String postalCode;
    private String address;
    private String detailAddress;
    private Integer defaultAddressStatus;
    private String recipient;
    private String recipientContact;
    private String deliveryRequest;
    private Long userId;

    public static DeliveryAddressVO toEntity(DeliveryAddrDto dto) {
        return DeliveryAddressVO.builder()
                .deliveryName(dto.getDeliveryName())
                .postalCode(dto.getPostalCode())
                .address(dto.getAddress())
                .detailAddress(dto.getDetailAddress())
                .defaultAddressStatus(dto.getDefaultAddressStatus())
                .recipient(dto.getRecipient())
                .recipientContact(dto.getRecipientContact())
                .deliveryRequest(dto.getDeliveryRequest())
                .build();
    }


}