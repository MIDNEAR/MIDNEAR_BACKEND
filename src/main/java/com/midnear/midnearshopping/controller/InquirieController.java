package com.midnear.midnearshopping.controller;

import com.midnear.midnearshopping.domain.dto.Inquiries.InquiriesDTO;
import com.midnear.midnearshopping.domain.dto.Inquiries.InquiriesListDTO;
import com.midnear.midnearshopping.domain.dto.Inquiries.Inquiry_commentsDTO;
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
    public ResponseEntity<InquiriesDTO> getInquirie(@RequestParam("inquiryId") Long inquiryId){
        InquiriesDTO inquiries = inquirieService.selectInquirie(inquiryId);
        if (inquiries != null) {
            return ResponseEntity.ok(inquiries);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    //  문의글 댓글 달기
    @PostMapping("/postInquirieComment")
    public ResponseEntity<String> postInquirieComment(@RequestBody Inquiry_commentsDTO inquiryCommentsDTO){
        Date today = new Date(System.currentTimeMillis());
        inquiryCommentsDTO.setReplyDate(today);
        inquirieService.insertInquirieComment(inquiryCommentsDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("댓글이 성공적으로 등록되었습니다.");
    }

//  문의글 댓글 수정
    @PutMapping("/putInquirieComment")
    public ResponseEntity<String> putInquirieComment(@RequestBody Inquiry_commentsDTO inquiryCommentsDTO){
        Date today = new Date(System.currentTimeMillis());
        inquiryCommentsDTO.setReplyDate(today);
        inquirieService.updateInquiryComment(inquiryCommentsDTO);
        return ResponseEntity.ok("댓글이 성공적으로 수정되었습니다.");
    }

//  문의List 전체, 최신순 필터링
    @GetMapping("/getInquiryList")
    public  ResponseEntity<Map<String, Object>> list(@RequestParam(value = "page", defaultValue = "1") int pageNumber) {

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
        return ResponseEntity.ok(response);
    }


//  문의List 답글 완료/대기 필터링
    @GetMapping("/getInquiryReplyList")
    public  ResponseEntity<Map<String, Object>> Inquirylist(@RequestParam(value = "page", defaultValue = "1") int pageNumber, @RequestParam(value = "hasReply")  String hasReply) {

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
        return ResponseEntity.ok(response);
    }
}
