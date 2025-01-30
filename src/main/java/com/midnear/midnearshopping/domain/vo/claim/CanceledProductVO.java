package com.midnear.midnearshopping.domain.vo.claim;

import com.midnear.midnearshopping.domain.dto.claim.CancelRequestDto;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CanceledProductVO {
    Long canceledProductId;
    Date cancellationRequestDate;
    String cancellationStatus;
    String cancelReason;
    Date cancellationApprovalDate;
    Long orderProductId;

    public static CanceledProductVO toEntity(CancelRequestDto dto) {
        return CanceledProductVO.builder()
                .cancellationRequestDate(new Date())
                .cancellationStatus("취소요청")
                .cancelReason(dto.getCancelReason())
                .cancellationApprovalDate(null)
                .orderProductId(dto.getOrderProductId())
                .build();
    }
}
