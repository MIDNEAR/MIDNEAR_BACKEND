package com.midnear.midnearshopping.controller;

import com.midnear.midnearshopping.domain.dto.Statistics.StatisticsDto;
import com.midnear.midnearshopping.domain.dto.category.CreateCategoryDto;
import com.midnear.midnearshopping.domain.dto.policies_info.PoliciesAndInfoDto;
import com.midnear.midnearshopping.domain.dto.storeImages.StoreImagesDto;
import com.midnear.midnearshopping.exception.ApiResponse;
import com.midnear.midnearshopping.service.StoreManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
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

    // 개인정보 처리 방침 관리 불러오기
    @GetMapping("/getPrivacyPolicy")
    public ResponseEntity<ApiResponse> getPrivacyPolicy() {
        try {
            PoliciesAndInfoDto policiesAndInfoDto = storeManagementService.getPrivacyPolicy();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "데이터 불러오기 성공", policiesAndInfoDto));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 이용 약관 관리 불러오기
    @GetMapping("/getTermsOfService")
    public ResponseEntity<ApiResponse> getTermsOfService() {
        try {
            PoliciesAndInfoDto policiesAndInfoDto = storeManagementService.getTermsOfService();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "데이터 불러오기 성공", policiesAndInfoDto));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 주소 및 사업자 정보 관리 불러오기
    @GetMapping("/getBusinessInfo")
    public ResponseEntity<ApiResponse> getBusinessInfo() {
        try {
            PoliciesAndInfoDto policiesAndInfoDto = storeManagementService.getBusinessInfo();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "데이터 불러오기 성공", policiesAndInfoDto));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 개인정보 수집 및 이용 목적 관리 불러오기
    @GetMapping("/getDataUsage")
    public ResponseEntity<ApiResponse> getDataUsage() {
        try {
            PoliciesAndInfoDto policiesAndInfoDto = storeManagementService.getDataUsage();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "데이터 불러오기 성공", policiesAndInfoDto));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    //개인정보 처리 방침 관리 수정
    @PostMapping("/updatePrivacyPolicy")
    public ResponseEntity<ApiResponse> updatePrivacyPolicy(@RequestBody PoliciesAndInfoDto policiesAndInfoDto) {
        try {
            policiesAndInfoDto.setType("privacy_policy");
            storeManagementService.insertData(policiesAndInfoDto);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "개인정보 처리 방침 관리 수정 완료", null));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    //이용 약관 관리 수정
    @PostMapping("/updateTermsOfService")
    public ResponseEntity<ApiResponse> updateTermsOfService(@RequestBody PoliciesAndInfoDto policiesAndInfoDto) {
        try {
            policiesAndInfoDto.setType("terms_of_service");
            storeManagementService.insertData(policiesAndInfoDto);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "이용 약관 관리 수정 완료", null));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 주소 및 사업자 정보 관리 수정
    @PostMapping("/updateBusinessInfo")
    public ResponseEntity<ApiResponse> updateBusinessInfo(@RequestBody PoliciesAndInfoDto policiesAndInfoDto) {
        try {
            policiesAndInfoDto.setType("business_info");
            storeManagementService.insertData(policiesAndInfoDto);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "주소 및 사업자 정보 관리 수정 완료", null));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    //개인정보 수집 및 이용 목적 관리 수정
    @PostMapping("/updateDataUsage")
    public ResponseEntity<ApiResponse> updateDataUsage(@RequestBody PoliciesAndInfoDto policiesAndInfoDto) {
        try {
            policiesAndInfoDto.setType("data_usage");
            storeManagementService.insertData(policiesAndInfoDto);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "개인정보 수집 및 이용 목적 관리 수정 완료", null));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 일간 매출 통계
    // 미래 시간 조회 요청 시 오류 반환
    @GetMapping("/getDailySales")
    public ResponseEntity<ApiResponse> getDailySales(@RequestParam(value = "startDate") Date startDate) {
        try {
            // startDate yyyy-MM-dd
            List<StatisticsDto> statisticsDtos = storeManagementService.getDailySales(startDate);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "일간 매출 데이터 불러오기 성공", statisticsDtos));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 주간 매출 통계
    // 미래 시간 조회 요청 시 오류 반환
    @GetMapping("/getWeeklySales")
    public ResponseEntity<ApiResponse> getWeeklySales(@RequestParam(value = "startDate") Date startDate) {
        try {
            // startDate yyyy-MM-01
            List<StatisticsDto> statisticsDtos = storeManagementService.getWeeklySales(startDate);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "일간 매출 데이터 불러오기 성공", statisticsDtos));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 월간 매출 통계
    // 미래 시간 조회 요청 시 오류 반환
    @GetMapping("/getMonthlySales")
    public ResponseEntity<ApiResponse> getMonthlySales(@RequestParam(value = "startDate") Date startDate) {
        try {
            // startDate yyyy-MM-01
            List<StatisticsDto> statisticsDtos = storeManagementService.getMonthlySales(startDate);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "월간 매출 데이터 불러오기 성공", statisticsDtos));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }
}
