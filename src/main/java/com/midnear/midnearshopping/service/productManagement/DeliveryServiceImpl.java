package com.midnear.midnearshopping.service.productManagement;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.midnear.midnearshopping.domain.dto.delivery.DeliveryInfoDTO;
import com.midnear.midnearshopping.mapper.productManagement.ReturnMapper;
import com.midnear.midnearshopping.mapper.productManagement.ShippingManagementMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryServiceImpl implements DeliveryService {

    private final ShippingManagementMapper shippingManagementMapper;
    private final RestTemplate restTemplate;
    private static final String SMART_TRACKING_API_URL = "http://info.sweettracker.co.kr/api/v1/trackingInfo";

    @Scheduled(fixedRate = 3600000)
    @Override
    public void updateTrackingStatus() {
        List<DeliveryInfoDTO> inTransitOrders = shippingManagementMapper.getInTransitOrders();

        for (DeliveryInfoDTO deliveryinfo : inTransitOrders) {
            String url = SMART_TRACKING_API_URL + "?t_code=" + deliveryinfo.getCarrierCode() + "&t_invoice=" + deliveryinfo.getInvoiceNumber() + "&t_key=" + "P4pBDvEWDxfhFhnnNRs9NA";

            try {
                ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);

                if (response.getStatusCode() == HttpStatus.OK) {
                    String status = parseStatusFromResponse(response.getBody());

                    if ("배송완료".equals(status)) {  // 스마트택배 API에서 "배송완료" 확인
                        try {
                            shippingManagementMapper.updateTrackingStatus(deliveryinfo.getDeliveryId());
                            log.info("배송완료 업데이트 성공");
                        } catch (Exception e) {
                            log.error("배송완료 업데이트 실패",e);
                        }
                    }
                } else {
                    log.warn("스마트택배 API 응답 오류");
                }
            } catch (Exception e) {
                log.error("스마트택배 API 호출 실패 : {}", e.getMessage(), e);
            }
        }

        }
    @Override
    public String parseStatusFromResponse(String responseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);

            if (jsonNode.has("lastStateDetail") && jsonNode.get("lastStateDetail").has("kind")) {
                return jsonNode.get("lastStateDetail").get("kind").asText();
            } else {
                log.warn("응답 내용: {}", responseBody);
                return "unknown";  // lastStateDetail이 없는 경우
            }
        } catch (Exception e) {
            log.error("JSON 파싱 오류: {}", e.getMessage(), e);
            return "unknown";
        }
    }

//  교환 배송상태 update
    @Override
    @Scheduled(fixedRate = 3600000)
    public void updateReturnTrackingStatus() {
        List<DeliveryInfoDTO> inTransitOrders = shippingManagementMapper.getReturnOrder();

        for (DeliveryInfoDTO deliveryinfo : inTransitOrders) {
            String url = SMART_TRACKING_API_URL + "?t_code=" + deliveryinfo.getCarrierCode() + "&t_invoice=" + deliveryinfo.getInvoiceNumber() + "&t_key=" + "P4pBDvEWDxfhFhnnNRs9NA";

            try {
                ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);

                if (response.getStatusCode() == HttpStatus.OK) {
                    String status = parseStatusFromResponse(response.getBody());

                    if ("배송완료".equals(status)) {  // 스마트택배 API에서 "배송완료" 확인
                        try {
                            shippingManagementMapper.updateReturnStatus(deliveryinfo.getReturnDeliveryId());
                            log.info("배송완료 업데이트 성공");
                        } catch (Exception e) {
                            log.error("배송완료 업데이트 실패",e);
                        }
                    }
                } else {
                    log.warn("스마트택배 API 응답 오류");
                }
            } catch (Exception e) {
                log.error("스마트택배 API 호출 실패 : {}", e.getMessage(), e);
            }
        }

    }
}


