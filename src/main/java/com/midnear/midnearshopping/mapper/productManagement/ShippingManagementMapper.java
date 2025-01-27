package com.midnear.midnearshopping.mapper.productManagement;

import com.midnear.midnearshopping.domain.dto.productManagement.ShippingManageDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShippingManagementMapper {
    // 배송비 업데이트
     void updateShipping(ShippingManageDTO ShippingManageDTO);
}
