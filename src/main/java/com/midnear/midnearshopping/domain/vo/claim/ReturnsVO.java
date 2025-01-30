package com.midnear.midnearshopping.domain.vo.claim;

import com.midnear.midnearshopping.domain.dto.claim.ReturnRequestDto;
import kotlin.contracts.Returns;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ReturnsVO {
    Long returnId;
    String returnStatus;
    String pickupMethod;
    Date returnRequestDate;
    Date returnCompleteDate;
    String returnReason;
    private Long orderProductId; // 필드명 수정 (대소문자 일관성 유지)

    public static ReturnsVO toEntity(ReturnRequestDto returnRequestDto) {
        return ReturnsVO.builder()
                .returnStatus("환불요청")
                .pickupMethod(returnRequestDto.getPickupMethod())
                .returnRequestDate(new Date()) // 현재 시간으로 설정
                .returnCompleteDate(null)
                .returnReason(returnRequestDto.getReturnReason())
                .orderProductId(returnRequestDto.getOrderProductId()) // 필드명 수정
                .build();
    }
}
