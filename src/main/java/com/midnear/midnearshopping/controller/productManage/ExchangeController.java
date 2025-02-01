package com.midnear.midnearshopping.controller.productManage;
import com.midnear.midnearshopping.domain.dto.productManagement.ExchangeDTO;
import com.midnear.midnearshopping.domain.dto.productManagement.ExchangeParamDTO;
import com.midnear.midnearshopping.domain.dto.productManagement.InvoiceInsertDTO;
import com.midnear.midnearshopping.domain.dto.productManagement.ParamDTO;
import com.midnear.midnearshopping.exception.ApiResponse;
import com.midnear.midnearshopping.service.productManagement.ExchangeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exchange")
@Slf4j
public class ExchangeController {
    private final ExchangeService exchangeService;

    //  교환 내역 최신순 조회
    @GetMapping("/getAll")
    @Transactional
    public ResponseEntity<ApiResponse> selectAll(@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber){
        try {
            //      페이징 번호에 맞는 List
            List<ExchangeDTO> exchange = exchangeService.selectAll(pageNumber);
            //      총 게시물 수
            int totalCount = exchangeService.count();

            //      총 페이지 수
            int totalPages = (int) Math.ceil((double) totalCount / 2);

            Map<String, Object> response = new HashMap<>();
            response.put("exchange", exchange);
            response.put("currentPage", pageNumber);
            response.put("totalPages", totalPages);
            response.put("totalCount", totalCount);

            // 200 OK 응답으로 JSON 반환
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "성공적으로 조회되었습니다.", response));

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    //   교환 내역 필터링 조회
    @GetMapping("/filterSearch")
    @Transactional
    public ResponseEntity<ApiResponse> filterSearch(@ModelAttribute @Valid ParamDTO ParamDTO){
        try {
            //      페이징 번호에 맞는 List
            List<ExchangeDTO> exchange = exchangeService.filterSearch(ParamDTO);
            //      총 게시물 수
            int totalCount = exchangeService.filterCount(ParamDTO);

            //      총 페이지 수
            int totalPages = (int) Math.ceil((double) totalCount / 2);

            Map<String, Object> response = new HashMap<>();
            response.put("exchange", exchange);
            response.put("currentPage", ParamDTO.getPageNumber());
            response.put("totalPages", totalPages);
            response.put("totalCount", totalCount);

            // 200 OK 응답으로 JSON 반환
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "성공적으로 조회되었습니다.", response));

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 반품으로 변경
    @PostMapping("/exchangeToRefund")
    public ResponseEntity<ApiResponse>updateExchange(@RequestBody ExchangeParamDTO exchangeParamDTO){
        try {
            exchangeService.updateExchange(exchangeParamDTO);

            // 200 OK 응답으로 JSON 반환
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "성공적으로 조회되었습니다.", null));

        }catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 상품 수거중
    @PutMapping("/pickupProduct")
    public ResponseEntity<ApiResponse> pickupProduct(@RequestBody InvoiceInsertDTO invoiceInsertDTO) {
        try {
            Long courierNumber = exchangeService.selectCarrierName(invoiceInsertDTO.getPickupCourier());

            if (courierNumber == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse(false, "존재하지 않는 택배사입니다.", null));
            }
            invoiceInsertDTO.setCarrierId(courierNumber);
            exchangeService.updatePickupStatus(invoiceInsertDTO);
            // 200 OK 응답으로 JSON 반환
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "성공적으로 수정되었습니다.", null));

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 선택상품 배송처리
    @PostMapping("/resendDelivery")
    public ResponseEntity<ApiResponse>selectDelivery(@RequestBody InvoiceInsertDTO invoiceInsertDTO){
        try {
            Long courierNumber = exchangeService.selectReturnCarrierName(invoiceInsertDTO.getResendCourier());

            if (courierNumber == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse(false, "존재하지 않는 택배사입니다.", null));
            }
            invoiceInsertDTO.setCarrierId(courierNumber);
            exchangeService.insertResendInfo(invoiceInsertDTO);
            // 200 OK 응답으로 JSON 반환
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "성공적으로 수정되었습니다.", null));

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }
}
