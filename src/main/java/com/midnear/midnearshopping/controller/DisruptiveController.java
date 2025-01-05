package com.midnear.midnearshopping.controller;
import com.midnear.midnearshopping.exception.ApiResponse;
import com.midnear.midnearshopping.service.DisruptiveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/disruptive")
@Slf4j
public class DisruptiveController {
    private final DisruptiveService disruptiveService;

    @GetMapping("/searchId")
    public ResponseEntity<ApiResponse> getInquirie(@RequestParam("id") String id ) {
        try {
            List<String> userID = disruptiveService.searchId(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "아이디 불러오기 성공.", userID));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }


}
