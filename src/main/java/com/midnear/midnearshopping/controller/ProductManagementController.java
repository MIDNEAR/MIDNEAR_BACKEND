package com.midnear.midnearshopping.controller;

import com.midnear.midnearshopping.domain.dto.category.CategoryDto;
import com.midnear.midnearshopping.domain.dto.coordinate.CoordinateDto;
import com.midnear.midnearshopping.domain.dto.coordinate.CoordinatedProductDto;
import com.midnear.midnearshopping.domain.dto.coordinate.MainProductDto;
import com.midnear.midnearshopping.domain.dto.products.ProductManagementListDto;
import com.midnear.midnearshopping.domain.dto.products.ProductsDto;
import com.midnear.midnearshopping.domain.dto.shipping_returns.ShippingReturnsDto;
import com.midnear.midnearshopping.domain.vo.shipping_returns.ShippingReturnsVo;
import com.midnear.midnearshopping.exception.ApiResponse;
import com.midnear.midnearshopping.service.ProductManagementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/productManagement")
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
    public ResponseEntity<ApiResponse> getProductList(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "23") int size,
            @RequestParam(name = "sortOrder", defaultValue = "최신순") String sortOrder,
            @RequestParam(name = "dateRange", defaultValue = "전체") String dateRange,
            @RequestParam(name = "searchRange", defaultValue = "") String searchRange,
            @RequestParam(name = "searchText", required = false) String searchText
    ) {
        try {
            //List<ProductsListDto> + 전체 페이지 수
            Map<String, Object> response = productManagementService.getProductList(page, size, sortOrder, dateRange, searchRange, searchText);
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
            productManagementService.setOnSale(saleList);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "판매중으로 변경 완료", null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    @PatchMapping("/setSoldOut")
    public ResponseEntity<ApiResponse> setSoldOut(@RequestBody List<Long> soldOutList) {
        try {
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
            productManagementService.updateShippingPolicy(shippingReturnsDto);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "shipping & returns 세부 약관 수정 완료", null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    @GetMapping("/getCoordinatedList")
    public ResponseEntity<ApiResponse> getCoordinatedList(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "4") int size,
            @RequestParam(name = "sortOrder", defaultValue = "최신순") String sortOrder,
            @RequestParam(name = "dateRange", defaultValue = "전체") String dateRange,
            @RequestParam(name = "searchRange", defaultValue = "") String searchRange,
            @RequestParam(name = "searchText", required = false) String searchText
    ) {
        try {
            Map<String, Object> response = productManagementService.getCoordinatedList(page, size, sortOrder, dateRange, searchRange, searchText);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "코디 상품 리스트 불러오기 성공", response));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    @GetMapping("/getCoordinatedProduct")
    public ResponseEntity<ApiResponse> getCoordinatedProduct(
            @RequestParam(name = "productColorId") Long productColorId
    ) {
        try {
            MainProductDto mainProductDto = productManagementService.getCoordinatedProduct(productColorId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "코디 상품 불러오기 성공", mainProductDto));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    @GetMapping("/searchCoordinatedProduct")
    public ResponseEntity<ApiResponse> searchCoordinatedProduct(
            @RequestParam(name = "productName", defaultValue = "") String productName
    ) {
        try {
            List<CoordinatedProductDto> response = productManagementService.searchCoordinatedProduct(productName);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "코디 상품 검색 성공", response));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    @PostMapping("/createCoordinate")
    public ResponseEntity<ApiResponse> createCoordinate(@RequestBody List<CoordinateDto> coordinateDtoList) {
        try {
            productManagementService.createCoordinate(coordinateDtoList);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "코디 상품 등록 성공", null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    @DeleteMapping("/deleteCoordinate")
    public ResponseEntity<ApiResponse> deleteCoordinate(@RequestBody List<CoordinateDto> deleteList) {
        try {
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
