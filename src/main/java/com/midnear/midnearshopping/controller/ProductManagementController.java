package com.midnear.midnearshopping.controller;

import com.midnear.midnearshopping.domain.dto.category.CategoryDto;
import com.midnear.midnearshopping.domain.products.ProductColorsListDto;
import com.midnear.midnearshopping.domain.products.ProductManagementListDto;
import com.midnear.midnearshopping.domain.products.ProductsDto;
import com.midnear.midnearshopping.exception.ApiResponse;
import com.midnear.midnearshopping.service.ProductManagementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductManagementController {
    private final ProductManagementService productManagementService;

    // 등록된 카테고리 목록 불러오기
    @GetMapping("/categories")
    public ResponseEntity<ApiResponse> getCategories() {
        try {
            List<CategoryDto> categories = productManagementService.getCategories();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "데이터 불러오기 성공", categories));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 상품 추가
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerProducts(@ModelAttribute @Valid ProductsDto productsDto) {
        try {
            productManagementService.registerProducts(productsDto);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "상품이 성공적으로 등록되었습니다.", null));
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage(), null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 상품 관리 목록 불러오기
    @GetMapping("/productList")
    public ResponseEntity<ApiResponse> getProductList() {
        try {
            List<ProductManagementListDto> productColorsList = productManagementService.getProductList();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "상품 리스트 조회 성공", productColorsList));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

}
