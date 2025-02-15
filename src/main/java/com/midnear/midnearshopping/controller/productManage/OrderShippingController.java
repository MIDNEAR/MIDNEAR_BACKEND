package com.midnear.midnearshopping.controller.productManage;
import com.midnear.midnearshopping.domain.dto.productManagement.*;
import com.midnear.midnearshopping.exception.ApiResponse;
import com.midnear.midnearshopping.service.productManagement.OrderShippingService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orderShipping")
@Slf4j
public class OrderShippingController {

    private final OrderShippingService orderShippingService;

    private boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(authentication.getName());
        return authentication != null && "admin".equals(authentication.getName());
    }



    //  주문 내역 최신순 조회
    @GetMapping("/getAll")
    @Transactional
    public ResponseEntity<ApiResponse> selectAll(@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber) {
        try {
            if (!isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
            }

            //      페이징 번호에 맞는 List
            List<OrderShippingDTO> orderShipping = orderShippingService.selectAll(pageNumber);
            //      총 게시물 수
            int totalCount = orderShippingService.count();

            //      총 페이지 수
            int totalPages = (int) Math.ceil((double) totalCount / 2);

            Map<String, Object> response = new HashMap<>();
            response.put("orderShipping", orderShipping);
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

    // 필터링 조회
    @GetMapping("/filterSearch")
    public ResponseEntity<ApiResponse> filterSearch(@ModelAttribute @Valid ParamDTO ParamDTO) {
        try {
            if (!isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
            }

            //      페이징 번호에 맞는 List
            List<OrderShippingDTO> orderShipping = orderShippingService.filterSearch(ParamDTO);

            //      총 게시물 수
            int totalCount = orderShippingService.filterCount(ParamDTO);

            //      총 페이지 수
            int totalPages = (int) Math.ceil((double) totalCount / 2);

            Map<String, Object> response = new HashMap<>();
            response.put("orderShipping", orderShipping);
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

    // 발주확인
    @PostMapping("/orderConfirmation")
    public ResponseEntity<ApiResponse> filterSearch(@RequestBody List<Long> orderProductId) {
        try {
            if (!isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
            }
            orderShippingService.updateConfirm(orderProductId);
            // 200 OK 응답으로 JSON 반환
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "성공적으로 수정되었습니다.", null));

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 송장번호 입력
    @PutMapping("/Invoice")
    public ResponseEntity<ApiResponse> filterSearch(@RequestBody InvoiceInsertDTO invoiceInsertDTO) {
        try {
            if (!isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
            }
            Long courierNumber = orderShippingService.selectCarrierName(invoiceInsertDTO.getCourier());

            if (courierNumber == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse(false, "존재하지 않는 택배사입니다.", null));
            }
            invoiceInsertDTO.setCarrierId(courierNumber);
            orderShippingService.insertInvoice(invoiceInsertDTO);
            // 200 OK 응답으로 JSON 반환
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "성공적으로 수정되었습니다.", null));

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }


    // 배송지연
    @PutMapping("/delayShipping")
    public ResponseEntity<ApiResponse> delayShipping(@RequestBody List<Long> orderProductId) {
        try {
            if (!isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
            }
            orderShippingService.delaySipping(orderProductId);
            // 200 OK 응답으로 JSON 반환
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "성공적으로 수정되었습니다.", null));

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 판매자 직접취소
    @PostMapping("/directCancel")
    public ResponseEntity<ApiResponse> directCancel(@RequestBody List<Long> orderProductId) {
        try {
            if (!isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
            }
            List<Long> orderCancelList = orderShippingService.selectCancelProduct(orderProductId);
            // 조회된 취소 완료된 상품이 있는지 검사
            for (Long id : orderProductId) {
                if (orderCancelList.contains(id)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ApiResponse(false, "이미 취소된 상품입니다", null));
                }
            }
            orderShippingService.directCancel(orderProductId);
            // 200 OK 응답으로 JSON 반환
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "성공적으로 수정되었습니다.", null));

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }

    }

    // 총수량 엑셀 다운로드
    @GetMapping("/exelDownload")
    public ResponseEntity<?> excelDownload(@RequestParam("orderProductId") List<Long> orderProductId) throws IOException {
        if (!isAdmin()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
        }
        try (Workbook wb = new XSSFWorkbook();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Sheet sheet = wb.createSheet("주문서");
            Row row = null;
            Cell cell = null;
            int rowNum = 0;

            List<OptionQuantityDTO> order = orderShippingService.selectOptionQuantity(orderProductId);

            // Header
            row = sheet.createRow(rowNum++);
            cell = row.createCell(0);
            cell.setCellValue("상품명");
            cell = row.createCell(1);
            cell.setCellValue("색상");
            cell = row.createCell(2);
            cell.setCellValue("옵션정보");
            cell = row.createCell(3);
            cell.setCellValue("수량");

            // Body
            for (int i = 0; i < order.size(); i++) {
                row = sheet.createRow(rowNum++);
                cell = row.createCell(0);
                cell.setCellValue(order.get(i).getProductName());
                cell = row.createCell(1);
                cell.setCellValue(order.get(i).getColor());
                cell = row.createCell(2);
                cell.setCellValue(order.get(i).getSize());
                cell = row.createCell(3);
                cell.setCellValue(order.get(i).getQuantity());
                cell = row.createCell(4);
            }

            // Excel 파일을 바이트 배열로 변환
            wb.write(outputStream);
            byte[] excelData = outputStream.toByteArray();

            // HTTP 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "OrderQuantity.xlsx");

            return ResponseEntity.status(HttpStatus.OK)
                    .headers(headers)
                    .body(excelData);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "엑셀 파일 생성 중 오류가 발생했습니다.", null));
        }

    }

    // 선택건 주문서 출력
    @GetMapping("/selectOrderRecipt")
    public ResponseEntity<ApiResponse> selectOrderRecipt(@RequestParam List<Long> orderProductId) {
        try {
            if (!isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
            }
            List<OrderReciptDTO> orderRecipt = orderShippingService.selectOrderRecipt(orderProductId);
            // 200 OK 응답으로 JSON 반환
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "성공적으로 출력되었습니다.", orderRecipt));

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 총수량 엑셀 다운로드
    @GetMapping("/AllExel")
    public ResponseEntity<?> AllExel(@RequestParam("orderProductId") List<Long> orderProductId) throws IOException {
        if (!isAdmin()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
        }
        try (Workbook wb = new XSSFWorkbook();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Sheet sheet = wb.createSheet("전체 주문서");
            Row row = null;
            Cell cell = null;
            int rowNum = 0;

            List<OptionQuantityDTO> order = orderShippingService.selectOrderDetails(orderProductId);

            // Header
            row = sheet.createRow(rowNum++);
            cell = row.createCell(0);
            cell.setCellValue("주문자명");
            cell = row.createCell(1);
            cell.setCellValue("수취인명");
            cell = row.createCell(2);
            cell.setCellValue("제품명");
            cell = row.createCell(3);
            cell.setCellValue("사이즈");
            cell = row.createCell(4);
            cell.setCellValue("컬러");
            cell = row.createCell(5);
            cell.setCellValue("수량");
            cell = row.createCell(6);
            cell.setCellValue("송장번호");

            // Body
            for (int i = 0; i < order.size(); i++) {
                row = sheet.createRow(rowNum++);
                cell = row.createCell(0);
                cell.setCellValue(order.get(i).getOrderName());
                cell = row.createCell(1);
                cell.setCellValue(order.get(i).getRecipientName());
                cell = row.createCell(2);
                cell.setCellValue(order.get(i).getProductName());
                cell = row.createCell(3);
                cell.setCellValue(order.get(i).getSize());
                cell = row.createCell(4);
                cell.setCellValue(order.get(i).getColor());
                cell = row.createCell(5);
                cell.setCellValue(order.get(i).getQuantity());
                cell = row.createCell(6);
                String invoiceNumber = order.get(i).getInvoiceNumber();

                if (invoiceNumber != null) {
                    cell.setCellValue(invoiceNumber); // Long -> long 자동 언박싱
                } else {
                    cell.setBlank(); // null 값인 경우 빈 셀로 설정
                }
            }

            // Excel 파일을 바이트 배열로 변환
            wb.write(outputStream);
            byte[] excelData = outputStream.toByteArray();

            // HTTP 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "Order.xlsx");

            return ResponseEntity.status(HttpStatus.OK)
                    .headers(headers)
                    .body(excelData);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "엑셀 파일 생성 중 오류가 발생했습니다.", null));
        }

    }

}

