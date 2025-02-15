package com.midnear.midnearshopping.service.productManagement;

import com.midnear.midnearshopping.domain.dto.delivery.FreeBasicConditionDTO;
import com.midnear.midnearshopping.domain.dto.productManagement.ShippingManageDTO;
import com.midnear.midnearshopping.mapper.productManagement.ShippingManagementMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShippingManageServiceImpl implements ShippingManageService {

    private final ShippingManagementMapper shippingManagementMapper;
    @Override
    public void updateShipping(ShippingManageDTO ShippingManageDTO) {
        shippingManagementMapper.updateShipping(ShippingManageDTO);
    }

    @Override
    public ShippingManageDTO selectShippingFee() {
        return shippingManagementMapper.selectShippingFee();
    }

    @Override
    public FreeBasicConditionDTO selectFreeBasicFee() {
        return shippingManagementMapper.selectFreeBasicFee();
    }
}
