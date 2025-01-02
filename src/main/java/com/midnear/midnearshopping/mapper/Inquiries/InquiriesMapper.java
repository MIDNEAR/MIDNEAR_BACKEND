package com.midnear.midnearshopping.mapper.Inquiries;

import com.midnear.midnearshopping.domain.dto.Inquiries.InquiriesDTO;
import com.midnear.midnearshopping.domain.dto.Inquiries.InquiriesListDTO;
import com.midnear.midnearshopping.domain.dto.Inquiries.Inquiry_commentsDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InquiriesMapper {

// 문의 글 하나 띄우기
   InquiriesDTO selectInquiries(Long inquiryId);

// 문의 글에 댓글달기
   void insertInquiryComment(Inquiry_commentsDTO inquiryCommentsDTO);

// 문의 댓글 수정
   void updateInquiryComment(Inquiry_commentsDTO inquiryCommentsDTO);

// 문의 최신순 전체 필터링

   List<InquiriesListDTO> SelectInquirylist(@Param("offset") int offset, @Param("pageSize") int pageSize);

   int count();

// 문의 답글완료/대기중 필터링
   List<InquiriesListDTO> SelectReplyInquirylist(@Param("offset") int offset, @Param("pageSize") int pageSize, @Param("hasReply")String hasReply);

   int countReply(@Param("hasReply")String hasReply);
}
