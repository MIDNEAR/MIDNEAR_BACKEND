package com.midnear.midnearshopping.controller.productManage;
import com.midnear.midnearshopping.domain.dto.productManagement.ConfirmPurchaseDTO;
import com.midnear.midnearshopping.domain.dto.productManagement.ParamDTO;
import com.midnear.midnearshopping.exception.ApiResponse;
import com.midnear.midnearshopping.service.productManagement.ConfirmPurchaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/confirmPurchase")
@Slf4j
public class ConfirmPurchaseController {

    private final ConfirmPurchaseService confirmPurchaseService;


    private boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(authentication.getName());
        return authentication != null && "admin".equals(authentication.getName());
    }


    //  구매확정 내역 최신순 조회
    @GetMapping("/getAll")
    @Transactional
    public ResponseEntity<ApiResponse> selectAll(@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber){
        try {
            if (!isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
            }

            //      페이징 번호에 맞는 List
            List<ConfirmPurchaseDTO> confirmPurchase = confirmPurchaseService.selectAll(pageNumber);
            //      총 게시물 수
            int totalCount = confirmPurchaseService.count();

            //      총 페이지 수
            int totalPages = (int) Math.ceil((double) totalCount / 2);

            Map<String, Object> response = new HashMap<>();
            response.put("confirmPurchase", confirmPurchase);
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

    // 구매확정 내역 필터링 조회
    @GetMapping("/filterSearch")
    public ResponseEntity<ApiResponse> filterSearch(@ModelAttribute @Valid ParamDTO ParamDTO){
        if (!isAdmin()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
        }

        //      페이징 번호에 맞는 List
        List<ConfirmPurchaseDTO> confirmPurchase = confirmPurchaseService.filterSearch(ParamDTO);

        //      총 게시물 수
        int totalCount = confirmPurchaseService.filterCount(ParamDTO);

        //      총 페이지 수
        int totalPages = (int) Math.ceil((double) totalCount / 2);

        Map<String, Object> response = new HashMap<>();
        response.put("confirmPurchase", confirmPurchase);
        response.put("currentPage", ParamDTO.getPageNumber());
        response.put("totalPages", totalPages);
        response.put("totalCount", totalCount);

        // 200 OK 응답으로 JSON 반환
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(true, "성공적으로 조회되었습니다.", response));

    }

}