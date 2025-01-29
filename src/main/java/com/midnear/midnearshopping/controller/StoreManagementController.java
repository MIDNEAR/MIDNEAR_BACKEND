package com.midnear.midnearshopping.controller;

import com.midnear.midnearshopping.domain.dto.category.CreateCategoryDto;
import com.midnear.midnearshopping.domain.dto.storeImages.StoreImagesDto;
import com.midnear.midnearshopping.exception.ApiResponse;
import com.midnear.midnearshopping.service.StoreManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/storeManagement")
public class StoreManagementController {
    private final StoreManagementService storeManagementService;

    // 메인 이미지 불러오기
    @GetMapping("/getMainImage")
    public ResponseEntity<ApiResponse> getMainImage() {
        try {
            StoreImagesDto storeImagesDto = storeManagementService.getMainImage();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "메인 이미지 불러오기 성공", storeImagesDto));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 로고 이미지 불러오기
    @GetMapping("/getLogoImage")
    public ResponseEntity<ApiResponse> getLogoImage() {
        try {
            StoreImagesDto storeImagesDto = storeManagementService.getLogoImage();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "로고 이미지 불러오기 성공", storeImagesDto));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 메인 이미지 수정
    @PostMapping("/modify/mainImage")
    public ResponseEntity<ApiResponse> modifyMainImage(@ModelAttribute StoreImagesDto storeImagesDto) {
        if (storeImagesDto.getFile().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "파일이 없습니다.", null));
        }
        try {
            storeManagementService.modifyMainImage(storeImagesDto);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "메인 이미지를 수정하였습니다.", null));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 로고 이미지 수정
    @PostMapping("/modify/logoImage")
    public ResponseEntity<ApiResponse> modifyLogoImage(@ModelAttribute StoreImagesDto storeImagesDto) {
        if (storeImagesDto.getFile().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "파일이 없습니다.", null));
        }
        try {
            storeManagementService.modifyLogoImage(storeImagesDto);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "로고 이미지를 수정하였습니다.", null));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    @PostMapping("/createNewCategory")
    public ResponseEntity<ApiResponse> createNewCategory(@RequestBody List<CreateCategoryDto> createCategoryDtoList) {
        try {
            storeManagementService.createNewCategory(createCategoryDtoList);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "카테고리 생성 완료", null));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }
}
