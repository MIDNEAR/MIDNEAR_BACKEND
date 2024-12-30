package com.midnear.midnearshopping.controller;

import com.midnear.midnearshopping.domain.dto.InquiriesDTO;
import com.midnear.midnearshopping.service.InquirieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inquirie")
@Slf4j
public class InquirieController {
    private final InquirieService inquirieService;

    @GetMapping("/getInquirie")
    public InquiriesDTO getInquirie(Long inquiry_id){
        return inquirieService.selectInquirie(inquiry_id);
    }
}
