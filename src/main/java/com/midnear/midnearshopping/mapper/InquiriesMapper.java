package com.midnear.midnearshopping.mapper;

import com.midnear.midnearshopping.domain.dto.InquiriesDTO;
import com.midnear.midnearshopping.domain.dto.Inquiry_commentsDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InquiriesMapper {

// 문의 글 하나 띄우기
   InquiriesDTO selectInquiries(Long inquiryId);

// 문의 글에 댓글달기
   void insertInquiryComment(Inquiry_commentsDTO inquiryCommentsDTO);

// 문의 댓글 수정
   void updateInquiryComment(Inquiry_commentsDTO inquiryCommentsDTO);
}
