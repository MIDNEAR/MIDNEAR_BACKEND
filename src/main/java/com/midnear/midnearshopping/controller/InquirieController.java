package com.midnear.midnearshopping.controller;
import com.midnear.midnearshopping.domain.dto.Inquiries.InquiriesDTO;
import com.midnear.midnearshopping.domain.dto.Inquiries.InquiriesListDTO;
import com.midnear.midnearshopping.domain.dto.Inquiries.Inquiry_commentsDTO;
import com.midnear.midnearshopping.exception.ApiResponse;
import com.midnear.midnearshopping.service.InquirieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/inquirie")
@Slf4j
public class InquirieController {

    private final InquirieService inquirieService;

    //  문의글 하나 띄우기
    @GetMapping("/getInquirie")
    public ResponseEntity<ApiResponse> getInquirie(@RequestParam("inquiryId") Long inquiryId){
        try {
            InquiriesDTO inquiries = inquirieService.selectInquirie(inquiryId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "아이디 불러오기 성공.", inquiries));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }

    }


    //  문의글 댓글 달기
    @PostMapping("/postInquirieComment")
    public ResponseEntity<ApiResponse> postInquirieComment(@RequestBody Inquiry_commentsDTO inquiryCommentsDTO){
        try {
            Date today = new Date(System.currentTimeMillis());
            inquiryCommentsDTO.setReplyDate(today);
            inquirieService.insertInquirieComment(inquiryCommentsDTO);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "댓글이 성공적으로 등록되었습니다..", null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    //  문의글 댓글 수정
    @PutMapping("/putInquirieComment")
    public ResponseEntity<ApiResponse> putInquirieComment(@RequestBody Inquiry_commentsDTO inquiryCommentsDTO){
        try {
            Date today = new Date(System.currentTimeMillis());
            inquiryCommentsDTO.setReplyDate(today);
            inquirieService.updateInquiryComment(inquiryCommentsDTO);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "댓글이 성공적으로 수정되었습니다.", null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    //  문의List 전체, 최신순 필터링
    @GetMapping("/getInquiryList")
    public ResponseEntity<ApiResponse> list(@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber) {
        try {
            //      페이징 번호에 맞는 문의글 List
            List<InquiriesListDTO> inquiryList = inquirieService.SelectInquirylist(pageNumber);

            //      총 게시물 수
            int totalCount = inquirieService.count();

            //      총 페이지 수
            int totalPages = (int) Math.ceil((double) totalCount / 2);

            Map<String, Object> response = new HashMap<>();
            response.put("inquiries", inquiryList);
            response.put("currentPage", pageNumber);
            response.put("totalPages", totalPages);
            response.put("totalCount", totalCount);

            // 200 OK 응답으로 JSON 반환
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "문의글 불러오기 성공.", response));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }


    //  문의List 답글 완료/대기 필터링
    @GetMapping("/getInquiryReplyList")
    public ResponseEntity<ApiResponse> Inquirylist(@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber, @RequestParam(value = "hasReply")  String hasReply) {
        try {
            //      페이징 번호에 맞는 문의글 List
            List<InquiriesListDTO> inquiryList = inquirieService.SelectReplyInquirylist(pageNumber,hasReply);

            //      총 게시물 수
            int totalCount = inquirieService.countReply(hasReply);

            //      총 페이지 수
            int totalPages = (int) Math.ceil((double) totalCount / 2);

            Map<String, Object> response = new HashMap<>();
            response.put("inquiries", inquiryList);
            response.put("currentPage", pageNumber);
            response.put("totalPages", totalPages);
            response.put("totalCount", totalCount);

            // 200 OK 응답으로 JSON 반환
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "문의글 불러오기 성공.", response));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    //  문의List 검색
    @GetMapping("/getInquirySearchList")
    public ResponseEntity<ApiResponse> SearchInquiries(@RequestParam(value = "page", defaultValue = "1") int pageNumber, @RequestParam(value = "search")String search, @RequestParam(value = "dateFilter")  String dateFilter, @RequestParam(value = "orderBy")String orderBy, @RequestParam(value = "searchValue")String searchValue) {
        try {
            //      페이징 번호에 맞는 문의글 List
            List<InquiriesListDTO> inquiryList = inquirieService.SearchInquiries(pageNumber,search,dateFilter,orderBy,searchValue);

            //      총 게시물 수
            int totalCount = inquirieService.searchCount(search,dateFilter,orderBy,searchValue);

            //      총 페이지 수
            int totalPages = (int) Math.ceil((double) totalCount / 2);

            Map<String, Object> response = new HashMap<>();
            response.put("inquiries", inquiryList);
            response.put("currentPage", pageNumber);
            response.put("totalPages", totalPages);
            response.put("totalCount", totalCount);

            // 200 OK 응답으로 JSON 반환
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "문의글 불러오기 성공.", response));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    //  문의글 전체/부분삭제
    @DeleteMapping("/deleteInquiryList")
    public ResponseEntity<ApiResponse> deleteInquiriy( @RequestBody List<Integer> inquiryId){
        try {
            inquirieService.deleteInquiriy(inquiryId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "문의글이 성공적으로 삭제되었습니다..", null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }
}