package com.midnear.midnearshopping.controller;

import com.midnear.midnearshopping.domain.dto.disruptive.disruptiveListDTO;
import com.midnear.midnearshopping.domain.dto.magazines.MagazinesListDTO;
import com.midnear.midnearshopping.exception.ApiResponse;
import com.midnear.midnearshopping.service.MagazinesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/magazine")
@Slf4j
public class MagazinesController {
    private final MagazinesService magazinesService;

//  매거진 List 전체 / 최신순 조회
    @GetMapping("/getMagazineList")
    public ResponseEntity<ApiResponse> getMagazineList(@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber){
        try {
            //      페이징 번호에 맞는 문의글 List
            List<MagazinesListDTO> magazinesList = magazinesService.selectMagazineList(pageNumber);

            //      총 게시물 수
            int totalCount = magazinesService.count();

            //      총 페이지 수
            int totalPages = (int) Math.ceil((double) totalCount / 2);

            Map<String, Object> response = new HashMap<>();
            response.put("inquiries", magazinesList);
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
}
