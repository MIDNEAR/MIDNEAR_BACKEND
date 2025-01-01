package com.midnear.midnearshopping.controller;

import com.midnear.midnearshopping.domain.dto.Inquiries.InquiriesDTO;
import com.midnear.midnearshopping.domain.dto.Inquiries.Inquiry_commentsDTO;
import com.midnear.midnearshopping.service.InquirieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Date;


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
}
