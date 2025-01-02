package com.midnear.midnearshopping.service;

import com.midnear.midnearshopping.domain.dto.Inquiries.InquiriesDTO;
import com.midnear.midnearshopping.domain.dto.Inquiries.InquiriesListDTO;
import com.midnear.midnearshopping.domain.dto.Inquiries.Inquiry_commentsDTO;
import com.midnear.midnearshopping.mapper.Inquiries.InquiriesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InquirieServiceImpl implements InquirieService {

    private final InquiriesMapper inquiriesMapper;
    private static final int pageSize = 2;

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

//  문의 게시글 전체 최신순 정렬
    @Override
    public List<InquiriesListDTO> SelectInquirylist(int pageNumber) {
        int offset = (pageNumber - 1) * pageSize;
        return inquiriesMapper.SelectInquirylist(offset, pageSize);
    }

    @Override
    public int count() {
        return inquiriesMapper.count();
    }

//  문의 게시글 답글 완료/대기 필터링

    @Override
    public List<InquiriesListDTO> SelectReplyInquirylist(int pageNumber, String hasReply) {
        int offset = (pageNumber - 1) * pageSize;
        return inquiriesMapper.SelectReplyInquirylist(offset,pageSize,hasReply);
    }

    @Override
    public int countReply(String hasReply) {
        return inquiriesMapper.countReply(hasReply);
    }

}
