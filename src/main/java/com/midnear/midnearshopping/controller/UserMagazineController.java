package com.midnear.midnearshopping.controller;

import com.midnear.midnearshopping.domain.dto.magazines.MagazineResponseDto;
import com.midnear.midnearshopping.domain.dto.magazines.MagazineResponseListDto;
import com.midnear.midnearshopping.exception.ApiResponse;
import com.midnear.midnearshopping.service.magazine.UserMagazineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userMagazine")
@RequiredArgsConstructor
public class UserMagazineController {
    private final UserMagazineService userMagazineService;

    // 매거진 목록 조회
    @GetMapping("/list")
    public ResponseEntity<ApiResponse> getMagazineLists(@RequestParam String sort) {
        try {
            List<MagazineResponseListDto> magazines = userMagazineService.getMagazineLists(sort);
            return ResponseEntity.ok(new ApiResponse(true, "Magazines retrieved successfully", magazines));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Failed to retrieve magazines: " + e.getMessage(), null));
        }
    }

    // 특정 매거진 조회
    @GetMapping("/detail")
    public ResponseEntity<ApiResponse> getMagazine(@RequestParam Long magazineId) {
        try {
            MagazineResponseDto magazine = userMagazineService.getMagazines(magazineId);
            return ResponseEntity.ok(new ApiResponse(true, "Magazine retrieved successfully", magazine));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Failed to retrieve magazine: " + e.getMessage() + e.toString(), null));
        }
    }

    // 매거진 검색 목록 조회
    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchMagazineLists(@RequestParam String sort, @RequestParam String searchValue) {
        try {
            List<MagazineResponseListDto> magazines = userMagazineService.searchMagazineLists( sort, searchValue);
            return ResponseEntity.ok(new ApiResponse(true, "Searched magazines retrieved successfully", magazines));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Failed to search magazines: " + e.getMessage(), null));
        }
    }

    // 매거진 조회수 증가
    @PatchMapping("/updateView")
    public ResponseEntity<ApiResponse> increaseMagazineView(@RequestParam Long magazineId) {
        try {
            userMagazineService.getMagazine(magazineId);
            return ResponseEntity.ok(new ApiResponse(true, "Magazine view count updated successfully", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Failed to update magazine view count: " + e.getMessage(), null));
        }
    }
}