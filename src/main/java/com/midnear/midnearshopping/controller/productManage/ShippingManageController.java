package com.midnear.midnearshopping.controller.productManage;

import com.midnear.midnearshopping.domain.dto.delivery.FreeBasicConditionDTO;
import com.midnear.midnearshopping.domain.dto.productManagement.ShippingManageDTO;
import com.midnear.midnearshopping.exception.ApiResponse;
import com.midnear.midnearshopping.service.productManagement.ShippingManageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shippingManage")
@Slf4j
public class ShippingManageController {

    private final ShippingManageService shippingManageService;

    @PutMapping("/updateShipping")
    public ResponseEntity<ApiResponse> updateShipping(@RequestBody ShippingManageDTO shippingManageDTO) {
        try {
            shippingManageService.updateShipping(shippingManageDTO);
            // 200 OK 응답으로 JSON 반환
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "성공적으로 수정되었습니다.", null));

            // 200 OK 응답으로 JSON 반환
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }

    }
    @GetMapping("/selectShippingFee")
    public ResponseEntity<ApiResponse> selectShippingFee() {
        try {
            ShippingManageDTO shippingManageDTO = shippingManageService.selectShippingFee();
            // 200 OK 응답으로 JSON 반환
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "성공적으로 가져왔습니다.", shippingManageDTO));

            // 200 OK 응답으로 JSON 반환
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }

    }

    @GetMapping("/selectFreeBasicFee")
    public ResponseEntity<ApiResponse> FreeBasicFee() {
        try {
            FreeBasicConditionDTO freeBasicConditionDTO = shippingManageService.selectFreeBasicFee();
            // 200 OK 응답으로 JSON 반환
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "성공적으로 가져왔습니다.", freeBasicConditionDTO));

            // 200 OK 응답으로 JSON 반환
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }

    }
}
