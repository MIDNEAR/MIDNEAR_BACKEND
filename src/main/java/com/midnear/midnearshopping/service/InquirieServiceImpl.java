package com.midnear.midnearshopping.service;

import com.midnear.midnearshopping.domain.dto.InquiriesDTO;
import com.midnear.midnearshopping.domain.dto.Inquiry_commentsDTO;
import com.midnear.midnearshopping.mapper.InquiriesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InquirieServiceImpl implements InquirieService {

    private final InquiriesMapper inquiriesMapper;

    @Override
    public InquiriesDTO selectInquirie(Long inquiryId) {
        System.out.println("inquiryId: " + inquiryId);
        return inquiriesMapper.selectInquiries(inquiryId);
    }

    @Override
    public void insertInquirieComment(Inquiry_commentsDTO inquiryCommentsDTO) {
        inquiriesMapper.insertInquries(inquiryCommentsDTO);
    }
}
