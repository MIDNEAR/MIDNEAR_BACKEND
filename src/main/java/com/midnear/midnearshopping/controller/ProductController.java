package com.midnear.midnearshopping.controller;

import com.midnear.midnearshopping.domain.dto.coordinate.CoordinateDto;
import com.midnear.midnearshopping.domain.dto.products.*;
import com.midnear.midnearshopping.exception.ApiResponse;
import com.midnear.midnearshopping.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    //조회 기준 기본값는 최신순으로 뒀습니당
    //하. 미친 인기순 개짜증남 고쳐야됨
    @GetMapping("/by-category")
    public ResponseEntity<?> getProductsByCategoryWithHierarchy(@RequestParam Long categoryId, @RequestParam int pageNumber, @RequestParam(defaultValue = "latest") String sort) {
        try{
            List<ProductsListDto> response = productService.getProductsByCategoryWithHierarchy(categoryId, pageNumber, sort);
            return ResponseEntity.ok(new ApiResponse(true, "상품 조회 성공", response));
        } catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, ex.getMessage(), null));
        }
    }

    @GetMapping("/detail")
    public ResponseEntity<?> getProductDetail(@RequestParam Long colorId) {
        try{
            ProductsDetailDto response = productService.getProductDetails(colorId);
            return ResponseEntity.ok(new ApiResponse(true, "상품 상세 정보 조회 성공", response));
        } catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, ex.getMessage(), null));
        }
    }
    @GetMapping("/coordinates")
    public ResponseEntity<?> getCoordinates(@RequestParam Long productColorId) {
        try{
            List<CoordinateProductDto> response = productService.getCoordinateProducts(productColorId);
            return ResponseEntity.ok(new ApiResponse(true, "코디상품 조회 완료", response));
        } catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, ex.getMessage(), null));
        }
    }

    @GetMapping("/totalAndPage")
    public ResponseEntity<?> getProductInfoList(@RequestParam Long categoryId) {
        try{
            ProductListInfoDto response = productService.getCategoryProductListInfo(categoryId);
            return ResponseEntity.ok(new ApiResponse(true, "카테고리별 전체 수 조회 완료", response));
        } catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, ex.getMessage() + ex.toString(), null));
        }
    }
}