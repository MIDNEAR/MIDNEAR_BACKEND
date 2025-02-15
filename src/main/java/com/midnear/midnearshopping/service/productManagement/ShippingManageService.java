package com.midnear.midnearshopping.service.productManagement;

import com.midnear.midnearshopping.domain.dto.delivery.FreeBasicConditionDTO;
import com.midnear.midnearshopping.domain.dto.productManagement.ShippingManageDTO;
import org.springframework.stereotype.Service;

@Service
public interface ShippingManageService {
    // 배송비 업데이트
    void updateShipping(ShippingManageDTO ShippingManageDTO);

    // 배송비 보기
    ShippingManageDTO selectShippingFee();

    // 기본, 무료배송 상태 보기
    FreeBasicConditionDTO selectFreeBasicFee();
}
