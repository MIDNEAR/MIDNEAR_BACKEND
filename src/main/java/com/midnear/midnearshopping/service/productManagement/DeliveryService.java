package com.midnear.midnearshopping.service.productManagement;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface DeliveryService {

//  전체 배송조회
    void updateTrackingStatus();

    String parseStatusFromResponse(String responseBody);

 //  환불 배송조회
    void updateReturnTrackingStatus();
}
