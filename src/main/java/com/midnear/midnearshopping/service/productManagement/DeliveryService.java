package com.midnear.midnearshopping.service.productManagement;
import org.springframework.stereotype.Service;


@Service
public interface DeliveryService {

    void updateTrackingStatus();

    String parseStatusFromResponse(String responseBody);
}
