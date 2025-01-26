package com.midnear.midnearshopping.controller.productManage;
import com.midnear.midnearshopping.domain.dto.productManagement.CancelProductDTO;
import com.midnear.midnearshopping.domain.dto.productManagement.ParamDTO;
import com.midnear.midnearshopping.domain.dto.productManagement.ReturnDTO;
import com.midnear.midnearshopping.domain.dto.productManagement.ReturnParamDTO;
import com.midnear.midnearshopping.exception.ApiResponse;
import com.midnear.midnearshopping.service.productManagement.ReturnService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.ReusableMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/return")
@Slf4j
public class ReturnController {

    private final ReturnService returnService;

    @GetMapping("/getAll")
    @Transactional
    public ResponseEntity<ApiResponse> sellectAll(@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber) {
        try {
            //      페이징 번호에 맞는 List
            List<ReturnDTO> returns = returnService.selectAll(pageNumber);
            //      총 게시물 수
            int totalCount = returnService.count();

            //      총 페이지 수
            int totalPages = (int) Math.ceil((double) totalCount / 2);

            Map<String, Object> response = new HashMap<>();
            response.put("returns", returns);
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

    // 필터링 검색
    @GetMapping("/filterSearch")
    @Transactional
    public ResponseEntity<ApiResponse> filterSearch(@ModelAttribute ParamDTO ParamDTO) {
        try {
            //      페이징 번호에 맞는 List
            List<ReturnDTO> returns = returnService.filterSearch(ParamDTO);
            //      총 게시물 수
            int totalCount = returnService.filterCount(ParamDTO);

            //      총 페이지 수
            int totalPages = (int) Math.ceil((double) totalCount / 2);

            Map<String, Object> response = new HashMap<>();
            response.put("returns", returns);
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

    // 선택상품 반품처리
    @PutMapping("/confirmReturn")
    public ResponseEntity<ApiResponse> confirmReturn(@RequestParam List<Long> returnId) {
        try {
            returnService.confirmReturn(returnId);
            // 200 OK 응답으로 JSON 반환
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "성공적으로 수정되었습니다.", null));

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 선택상품 반품 거부처리
    @PutMapping("/denayReturn")
    public ResponseEntity<ApiResponse> denayReturn(@RequestBody ReturnParamDTO returnParamDTO) {
        try {
            returnService.denayReturn(returnParamDTO);
            // 200 OK 응답으로 JSON 반환
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "성공적으로 수정되었습니다.", null));

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 선택상품 교환처리
    @PostMapping ("/returnToExchange")
    public ResponseEntity<ApiResponse> returnToExchange(@RequestBody List<Long> returnId) {
        try {
            returnService.updateEx(returnId);
            // 200 OK 응답으로 JSON 반환
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "성공적으로 수정되었습니다.", null));

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }
}
