package com.midnear.midnearshopping.service.productManagement;

import com.midnear.midnearshopping.domain.dto.productManagement.ShippingManageDTO;
import org.springframework.stereotype.Service;

@Service
public interface ShippingManageService {
    // 배송비 업데이트
    void updateShipping(ShippingManageDTO ShippingManageDTO);
}
