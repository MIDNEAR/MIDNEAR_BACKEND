package com.midnear.midnearshopping.controller;
import com.midnear.midnearshopping.domain.dto.disruptive.disruptiveDTO;
import com.midnear.midnearshopping.domain.dto.disruptive.disruptiveListDTO;
import com.midnear.midnearshopping.exception.ApiResponse;
import com.midnear.midnearshopping.service.disruptive.DisruptiveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/disruptive")
@Slf4j
public class DisruptiveController {
    private final DisruptiveService disruptiveService;


    private boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(authentication.getName());
        return authentication != null && "admin".equals(authentication.getName());
    }

    //  아이디 검색
    @GetMapping("/searchId/{id}")
    public ResponseEntity<ApiResponse> getInquirie(@PathVariable("id") String id) {
        try {
            if (!isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
            }
            List<String> userID = disruptiveService.searchId(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "아이디 불러오기 성공.", userID));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    //  판매 방해고객 등록
    @PostMapping("/insertDisrupt")
    public ResponseEntity<ApiResponse> insertDisruptive(@RequestBody disruptiveDTO disruptiveDTO) {
        try {
            if (!isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
            }
            disruptiveService.insertDisruptive(disruptiveDTO);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "제한 아이디 등록 성공.", null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    //  판매고객List 전체, 최신순 필터링
    @GetMapping("/getDisruptList")
    public ResponseEntity<ApiResponse> list(@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber) {
        try {
            if (!isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
            }
            //      페이징 번호에 맞는 List
            List<disruptiveListDTO> disruptive = disruptiveService.SelectDisruptlist(pageNumber);

            //      총 게시물 수
            int totalCount = disruptiveService.count();

            //      총 페이지 수
            int totalPages = (int) Math.ceil((double) totalCount / 2);

            Map<String, Object> response = new HashMap<>();
            response.put("disruptive", disruptive);
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

    //  판매 방해고객 List 검색
    @GetMapping("/getDisruptSearchList")
    public ResponseEntity<ApiResponse> getDisruptSearchList(@RequestParam(value = "page", defaultValue = "1") int pageNumber, @RequestParam(value = "dateFilter") String dateFilter, @RequestParam(value = "orderBy") String orderBy, @RequestParam(value = "search") String search, @RequestParam(value = "searchValue") String searchValue) {

            try {
                if (!isAdmin()) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
                }
                //      페이징 번호에 맞는  List
                List<disruptiveListDTO> disruptive = disruptiveService.disruptiveSearch(pageNumber, dateFilter, orderBy, search, searchValue);

                //      총 게시물 수
                int totalCount = disruptiveService.searchCount(dateFilter, orderBy, search, searchValue);

                //      총 페이지 수
                int totalPages = (int) Math.ceil((double) totalCount / 2);

                Map<String, Object> response = new HashMap<>();
                response.put("inquiries", disruptive);
                response.put("currentPage", pageNumber);
                response.put("totalPages", totalPages);
                response.put("totalCount", totalCount);

                // 200 OK 응답으로 JSON 반환
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ApiResponse(true, "성공적으로 조회되었습니다.", response));
            }catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
            }
    }

//  판매방해 고객 제한해제
    @DeleteMapping("/deleteDisruptSearchList")
    public ResponseEntity<ApiResponse> deleteDisrupt(@RequestBody List<Integer> disruptiveCustomerId){
        try {
            if (!isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
            }
            disruptiveService.deleteDisrupt(disruptiveCustomerId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "성공적으로 삭제되었습니다.", null));
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }
}


