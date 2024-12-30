package com.midnear.midnearshopping.controller;

import com.midnear.midnearshopping.domain.dto.InquiriesDTO;
import com.midnear.midnearshopping.domain.dto.Inquiry_commentsDTO;
import com.midnear.midnearshopping.service.InquirieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController
@RequiredArgsConstructor
@RequestMapping("/inquirie")
@Slf4j
public class InquirieController {
    private final InquirieService inquirieService;

    @GetMapping("/getInquirie")
    public InquiriesDTO getInquirie(Long inquiryId){
        return inquirieService.selectInquirie(inquiryId);
    }

    @PostMapping("/putInquirieComment")
    public void putInquirieComment(@RequestBody Inquiry_commentsDTO inquiryCommentsDTO){
        inquiryCommentsDTO.setReplyDate(new Date());
        inquirieService.insertInquirieComment(inquiryCommentsDTO);
    }

}
