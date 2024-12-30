package com.midnear.midnearshopping.service;

import com.midnear.midnearshopping.domain.dto.InquiriesDTO;
import com.midnear.midnearshopping.domain.dto.Inquiry_commentsDTO;
import org.springframework.stereotype.Service;

@Service
public interface InquirieService {
//  문의 게시글 하나 띄우기
    InquiriesDTO selectInquirie(Long inquiryId);

//  문의 게시글에 댓글 작성하기
    void insertInquirieComment(Inquiry_commentsDTO inquiryCommentsDTO);

}
