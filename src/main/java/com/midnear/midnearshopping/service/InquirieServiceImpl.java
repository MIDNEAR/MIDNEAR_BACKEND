package com.midnear.midnearshopping.service;

import com.midnear.midnearshopping.domain.dto.InquiriesDTO;
import com.midnear.midnearshopping.mapper.InquiriesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InquirieServiceImpl implements InquirieService {

    private final InquiriesMapper inquiriesMapper;

    @Override
    public InquiriesDTO selectInquirie(Long inquiry_id) {
        System.out.println("inquiryId: " + inquiry_id);
        return inquiriesMapper.selectInquiries(inquiry_id);
    }
}
