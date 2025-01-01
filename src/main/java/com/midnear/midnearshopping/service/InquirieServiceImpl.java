package com.midnear.midnearshopping.service;

import com.midnear.midnearshopping.domain.dto.Inquiries.InquiriesDTO;
import com.midnear.midnearshopping.domain.dto.Inquiries.Inquiry_commentsDTO;
import com.midnear.midnearshopping.mapper.InquiriesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InquirieServiceImpl implements InquirieService {

    private final InquiriesMapper inquiriesMapper;

//  문의 글 하나 띄우기
    @Override
    public InquiriesDTO selectInquirie(Long inquiryId) {
        System.out.println("inquiryId: " + inquiryId);
        return inquiriesMapper.selectInquiries(inquiryId);
    }

//  문의 글에 댓글 달기
    @Override
    public void insertInquirieComment(Inquiry_commentsDTO inquiryCommentsDTO) {
        inquiriesMapper.insertInquiryComment(inquiryCommentsDTO);
    }

//  문의 댓글 수정
    @Override
    public void updateInquiryComment(Inquiry_commentsDTO inquiryCommentsDTO) {
        inquiriesMapper.updateInquiryComment(inquiryCommentsDTO);
    }
}
