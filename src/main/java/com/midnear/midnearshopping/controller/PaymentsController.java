package com.midnear.midnearshopping.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.midnear.midnearshopping.domain.dto.payment.PaymentDbDTO;
import com.midnear.midnearshopping.domain.dto.payment.SaveAmountRequestDTO;
import com.midnear.midnearshopping.domain.dto.payment.TossPaymentResponseDTO;
import com.midnear.midnearshopping.exception.PaymentErrorResponse;

import com.midnear.midnearshopping.service.order.paymentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
@Slf4j
public class PaymentsController {

    private final RestTemplate restTemplate;
    private static final String TOSS_PAYMENT_CONFIRM_URL = "https://api.tosspayments.com/v1/payments/confirm";
    private static final String SECRET_KEY = "YOUR_SECRET_KEY"; // 토스에서 발급한 시크릿 키

    private final paymentService paymentService;

    // orderId, amount(결제금액) 받아오기
    @PostMapping("/saveAmount")
    public ResponseEntity<?> tempsave(HttpSession session, @RequestBody SaveAmountRequestDTO saveAmountRequest) {
        session.setAttribute(saveAmountRequest.getOrderId(), saveAmountRequest.getAmount());
        return ResponseEntity.ok("Payment temp save successful");
    }

    // 실결제 금액과 같은지 검증
    @PostMapping("/verifyAmount")
    public ResponseEntity<?> verifyAmount(HttpSession session, @RequestBody SaveAmountRequestDTO saveAmountRequest) {

        BigDecimal amount = (BigDecimal) session.getAttribute(saveAmountRequest.getOrderId());
        // 결제 전의 금액과 결제 후의 금액이 같은지 검증
        if(amount == null || !amount.equals(saveAmountRequest.getAmount()))
            return ResponseEntity.badRequest().body(PaymentErrorResponse.builder().code(400).message("결제 금액 정보가 유효하지 않습니다.").build());

        // 검증에 사용했던 세션은 삭제
        session.removeAttribute(saveAmountRequest.getOrderId());

        return ResponseEntity.ok("Payment is valid");
    }

    // 결제승인
    @PostMapping("/confirm")
    public ResponseEntity<TossPaymentResponseDTO> confirmPayment(@RequestBody TossPaymentResponseDTO confirmPaymentRequest) throws Exception {
            try {
                //Secret Key
                String secretKey = "Basic " + SECRET_KEY;

                // HTTP 요청 헤더 설정
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.set("Authorization",secretKey);

                HttpEntity<TossPaymentResponseDTO> httpEntity = new HttpEntity<>(confirmPaymentRequest, headers);

                //토스 결제 승인 API 호출
                ResponseEntity<String> response = restTemplate.exchange(
                        TOSS_PAYMENT_CONFIRM_URL,
                        HttpMethod.POST,
                        httpEntity,
                        String.class
                );

                if (response.getStatusCode() == HttpStatus.OK) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(response.getBody());

                    TossPaymentResponseDTO paymentResponse = new TossPaymentResponseDTO(
                            jsonNode.get("paymentKey").asText(),
                            jsonNode.get("orderId").asText(),
                            new BigDecimal(jsonNode.get("amount").asText()),
                            jsonNode.get("status").asText()
                    );


                    try {
                        PaymentDbDTO paymentDbDTO = PaymentDbDTO.builder()
                                        .paymentStatus(jsonNode.get("status").asText())
                                        .tossPaymentKey(jsonNode.get("paymentKey").asText())
                                        .tossPaymentMethod(jsonNode.get("method").asText())
                                        .totalOrderPayment(jsonNode.get("totalAmount").decimalValue())
                                        .tossOrderId(jsonNode.get("orderId").asText())
                                        .build();

                        paymentService.updatePayment(paymentDbDTO);

                        return ResponseEntity.ok(paymentResponse);
                    } catch (Exception e) {
                        log.error("DB 저장 실패, 결제 취소 실행", e);


                        requestPaymentCancel(confirmPaymentRequest.getPaymentKey(), "DB 저장 실패");
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
                    }
                } else {
                    log.error("토스 결제 승인 실패: {}", response.getBody());
                    return null;
                }
            } catch (Exception e) {
                log.error("토스 결제 승인 중 오류 발생", e);
                return null;
            }
        }

//  결제취소
    public ResponseEntity<String> requestPaymentCancel(String paymentKey, String cancelReason) {
        try {
            String secretKey = "Basic " + SECRET_KEY;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", secretKey);

            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("cancelReason", cancelReason);

            HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    "https://api.tosspayments.com/v1/payments/" + paymentKey + "/cancel",
                    HttpMethod.POST,
                    request,
                    String.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                log.info("결제 취소 성공: {}", response.getBody());
                return ResponseEntity.ok("결제가 성공적으로 취소되었습니다.");
            } else {
                log.error(" 결제 취소 실패: {}", response.getBody());
                return ResponseEntity.status(response.getStatusCode()).body("결제 취소 실패");
            }
        } catch (Exception e) {
            log.error("결제 취소 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("결제 취소 중 오류 발생");
        }
    }

}
