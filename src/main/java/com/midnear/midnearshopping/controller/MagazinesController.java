package com.midnear.midnearshopping.controller;

import com.midnear.midnearshopping.domain.dto.magazines.MagazineImagesDTO;
import com.midnear.midnearshopping.domain.dto.magazines.MagazinesDTO;
import com.midnear.midnearshopping.domain.dto.magazines.MagazinesListDTO;
import com.midnear.midnearshopping.exception.ApiResponse;
import com.midnear.midnearshopping.service.magazine.MagazinesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ApiResponse> getMagazineList(@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber) {
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

    //  매거진 List 필터링 검색
    @GetMapping("/getMagazineSearchList")
    public ResponseEntity<ApiResponse> getMagazineSearchList(@RequestParam(value = "page", defaultValue = "1") int pageNumber, @RequestParam(value = "dateFilter") String dateFilter, @RequestParam(value = "orderBy") String orderBy, @RequestParam(value = "search") String search, @RequestParam(value = "searchValue") String searchValue) {

        try {
            //      페이징 번호에 맞는  List
            List<MagazinesListDTO> magazineList = magazinesService.magazineSearch(pageNumber, dateFilter, orderBy, search, searchValue);

            //      총 게시물 수
            int totalCount = magazinesService.searchCount(dateFilter, orderBy, search, searchValue);

            //      총 페이지 수
            int totalPages = (int) Math.ceil((double) totalCount / 2);

            Map<String, Object> response = new HashMap<>();
            response.put("magazinList", magazineList);
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

//  매거진 전체/부분 삭제
//  수정필요 매거진 삭제시 매거진 이미지도 트렌젝션으로 삭제하도록
    @DeleteMapping("/deleteMagazines")
    public ResponseEntity<ApiResponse> deleteMagazine(@RequestBody List<Long> magazineId){
        try {
            magazinesService.deleteMagazine(magazineId);
            // 200 OK 응답으로 JSON 반환
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "성공적으로 삭제되었습니다.", null));

        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

//  매거진 작성
    @PostMapping(value = "/insertMagazine")
    public ResponseEntity<ApiResponse> insertMagazine(@ModelAttribute  MagazinesDTO magazinesDTO){
        try {
            magazinesService.insertMagazine(magazinesDTO);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "작성이 완료되었습니다.", null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, ex.getMessage(), null));
        }
    }

// 작성한 매거진 가져오기
    @Transactional
    @GetMapping("/selectMagazine/{magazineId}")
    public ResponseEntity<ApiResponse> selectMagazine(@PathVariable("magazineId")  Long magazineId){
        try {
            MagazinesDTO magazines =  magazinesService.selectMagazine(magazineId);
            List<MagazineImagesDTO> magazineImage= magazinesService.selectMagazineImage(magazineId);
            Map<String, Object> response = new HashMap<>();

            response.put("magazine", magazines);
            response.put("magazineImage", magazineImage);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "불러오기에 성공했습니다.", response));
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

// 작성한 메거진 수정하기
    @PutMapping("/updateMagazine")
    public ResponseEntity<ApiResponse> updateMagazine(@ModelAttribute  MagazinesDTO magazinesDTO){
        try {
            magazinesService.updateMagazine(magazinesDTO);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "작성이 완료되었습니다.", null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, ex.getMessage(), null));
        }
    }
}
