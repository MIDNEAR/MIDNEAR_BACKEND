package com.midnear.midnearshopping.controller;

import com.midnear.midnearshopping.domain.dto.category.CategoryDto;
import com.midnear.midnearshopping.domain.dto.coordinate.CoordinateDto;
import com.midnear.midnearshopping.domain.dto.coordinate.CoordinatedProductDto;
import com.midnear.midnearshopping.domain.dto.coordinate.MainProductDto;
import com.midnear.midnearshopping.domain.dto.products.ProductsDto;
import com.midnear.midnearshopping.domain.dto.shipping_returns.ShippingReturnsDto;
import com.midnear.midnearshopping.domain.vo.shipping_returns.ShippingReturnsVo;
import com.midnear.midnearshopping.exception.ApiResponse;
import com.midnear.midnearshopping.service.ProductManagementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/productManagement")
@Slf4j
public class ProductManagementController {
    private final ProductManagementService productManagementService;

    private boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(authentication.getName());
        return authentication != null && "admin".equals(authentication.getName());
    }

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
    @PostMapping("/registerProducts")
    public ResponseEntity<ApiResponse> registerProducts(@ModelAttribute @Valid ProductsDto productsDto) {
        try {
            if (!isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
            }
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
    public ResponseEntity<ApiResponse> getProductList(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "sortOrder", defaultValue = "최신순") String sortOrder,
            @RequestParam(name = "dateRange", defaultValue = "전체") String dateRange,
            @RequestParam(name = "searchRange", defaultValue = "") String searchRange,
            @RequestParam(name = "searchText", required = false) String searchText
    ) {
        try {
            if (!isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
            }
            //List<ProductsListDto> + 전체 페이지 수
            Map<String, Object> response = productManagementService.getProductList(page, sortOrder, dateRange, searchRange, searchText);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "상품 리스트 조회 성공", response));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 판매중으로 변경
    @PatchMapping("/setOnSale")
    public ResponseEntity<ApiResponse> setOnSale(@RequestBody List<Long> saleList) {
        try {
            if (!isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
            }
            productManagementService.setOnSale(saleList);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "판매중으로 변경 완료", null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    @PatchMapping("/setSoldOut")
    public ResponseEntity<ApiResponse> setSoldOut(@RequestBody List<Long> soldOutList) {
        try {
            if (!isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
            }
            productManagementService.setSoldOut(soldOutList);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "sold out 처리 완료", null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 판매 중단(숨김 처리로 변경)
    @PatchMapping("/setDiscontinued")
    public ResponseEntity<ApiResponse> setDiscontinued(@RequestBody List<Long> discontinuedList) {
        try {
            if (!isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
            }
            productManagementService.setDiscontinued(discontinuedList);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "숨김 처리 완료", null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 상품 삭제
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteProducts(@RequestBody List<Long> deleteList) {
        try {
            if (!isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
            }
            productManagementService.deleteProducts(deleteList);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "상품 삭제 완료", null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 수정할 상품 창 로드
    @GetMapping("/modify/{productId}")
    public ResponseEntity<ApiResponse> getProduct(@PathVariable Long productId) {
        try {
            if (!isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
            }
            ProductsDto productsDto = productManagementService.getProduct(productId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "상품 정보 가져오기 성공", productsDto));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    @PutMapping("/modify")
    public ResponseEntity<ApiResponse> modifyProduct(@ModelAttribute ProductsDto productsDto) {
        try {
            if (!isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
            }
            productManagementService.modifyProduct(productsDto);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "상품을 성공적으로 수정하였습니다.", null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    @DeleteMapping("/deleteColors")
    public ResponseEntity<ApiResponse> deleteColors(@RequestBody List<Long> deleteList) {
        try {
            if (!isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
            }
            productManagementService.deleteColors(deleteList);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "컬러 삭제 완료.", null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    @DeleteMapping("/deleteSizes")
    public ResponseEntity<ApiResponse> deleteSizes(@RequestBody List<Long> deleteList) {
        try {
            if (!isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
            }
            productManagementService.deleteSizes(deleteList);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "사이즈 삭제 완료.", null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }



    // shipping & returns 데이터 불러오기
    @GetMapping("/shippingReturns")
    public ResponseEntity<ApiResponse> getShippingInfo() {
        try {
            ShippingReturnsVo shippingReturnsVo = productManagementService.getShippingReturnsVo();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "데이터 불러오기 성공", shippingReturnsVo));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // shipping & returns (택배사 및 번호, 배송관련 유의사항) 수정
    @PatchMapping("/updateShippingReturns")
    public ResponseEntity<ApiResponse> updateShippingInfo(@RequestBody ShippingReturnsDto shippingReturnsDto) {
        try {
            if (!isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
            }
            productManagementService.updateShippingReturns(shippingReturnsDto);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "shipping & returns (택배사 및 번호, 배송관련 유의사항) 수정 완료", null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // shipping & returns 세부 약관 수정
    @PatchMapping("/updateShippingPolicy")
    public ResponseEntity<ApiResponse> updateShippingPolicy(@RequestBody ShippingReturnsDto shippingReturnsDto) {
        try {
            if (!isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
            }
            productManagementService.updateShippingPolicy(shippingReturnsDto);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "shipping & returns 세부 약관 수정 완료", null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 코디상품 리스트 조회
    @GetMapping("/getCoordinatedList")
    public ResponseEntity<ApiResponse> getCoordinatedList(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "sortOrder", defaultValue = "최신순") String sortOrder,
            @RequestParam(name = "dateRange", defaultValue = "전체") String dateRange,
            @RequestParam(name = "searchRange", defaultValue = "") String searchRange,
            @RequestParam(name = "searchText", required = false) String searchText
    ) {
        try {
            if (!isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
            }
            Map<String, Object> response = productManagementService.getCoordinatedList(page, sortOrder, dateRange, searchRange, searchText);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "코디 상품 리스트 불러오기 성공", response));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 코디 상품 정보 조회
    @GetMapping("/getCoordinatedProduct")
    public ResponseEntity<ApiResponse> getCoordinatedProduct(
            @RequestParam(name = "productColorId") Long productColorId
    ) {
        try {
            if (!isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
            }
            MainProductDto mainProductDto = productManagementService.getCoordinatedProduct(productColorId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "코디 상품 불러오기 성공", mainProductDto));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 코디 상품 검색
    @GetMapping("/searchCoordinatedProduct")
    public ResponseEntity<ApiResponse> searchCoordinatedProduct(
            @RequestParam(name = "productName", defaultValue = "") String productName
    ) {
        try {
            if (!isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
            }
            List<CoordinatedProductDto> response = productManagementService.searchCoordinatedProduct(productName);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "코디 상품 검색 성공", response));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 코디 상품으로 등록
    @PostMapping("/createCoordinate")
    public ResponseEntity<ApiResponse> createCoordinate(@RequestBody List<CoordinateDto> coordinateDtoList) {
        try {
            if (!isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
            }
            productManagementService.createCoordinate(coordinateDtoList);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "코디 상품 등록 성공", null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 코디 상품 해제
    @DeleteMapping("/deleteCoordinate")
    public ResponseEntity<ApiResponse> deleteCoordinate(@RequestBody List<CoordinateDto> deleteList) {
        try {
            if (!isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
            }
            productManagementService.deleteCoordinate(deleteList);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "코디 상품 삭제 성공", null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

}
