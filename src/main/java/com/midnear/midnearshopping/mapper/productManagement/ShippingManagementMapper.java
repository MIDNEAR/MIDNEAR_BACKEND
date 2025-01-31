package com.midnear.midnearshopping.mapper.productManagement;

import com.midnear.midnearshopping.domain.dto.delivery.DeliveryInfoDTO;
import com.midnear.midnearshopping.domain.dto.productManagement.ShippingManageDTO;
import com.midnear.midnearshopping.domain.vo.delivery.DeliveryInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ShippingManagementMapper {
    // 배송비 업데이트
     void updateShipping(ShippingManageDTO ShippingManageDTO);

    // 배송정보 업데이트
    List<DeliveryInfoDTO> getInTransitOrders();
    void updateTrackingStatus(@Param("deliveryId") Long deliveryId);

    List<DeliveryInfoDTO> getReturnOrder();

    void updateReturnStatus(@Param("returnId") Long returnId);
}
