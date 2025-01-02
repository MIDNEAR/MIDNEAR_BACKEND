package com.midnear.midnearshopping.service;

import com.midnear.midnearshopping.domain.dto.Inquiries.InquiriesDTO;
import com.midnear.midnearshopping.domain.dto.Inquiries.InquiriesListDTO;
import com.midnear.midnearshopping.domain.dto.Inquiries.Inquiry_commentsDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InquirieService {
//  문의 게시글 하나 띄우기
    InquiriesDTO selectInquirie(Long inquiryId);

//  문의 게시글에 댓글 작성하기
    void insertInquirieComment(Inquiry_commentsDTO inquiryCommentsDTO);

//  문의 게시글 댓글 수정
    void updateInquiryComment(Inquiry_commentsDTO inquiryCommentsDTO);

//  문의 게시글 List 띄우기
    List<InquiriesListDTO> SelectInquirylist(int pageNumber);

//  문의 게시글 전체 개수
    int count();
}