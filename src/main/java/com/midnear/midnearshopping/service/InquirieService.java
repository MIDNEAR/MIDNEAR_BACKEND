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

//  문의 게시글 전체,최신순 필터링
    List<InquiriesListDTO> SelectInquirylist(int pageNumber);
    int count();

//  문의 게시글 답글 완료/대기 필터링
    List<InquiriesListDTO> SelectReplyInquirylist(int pageNumber, String hasReply);
    int countReply(String hasReply);

//   문의 게시글 검색
     List<InquiriesListDTO>WriterSearchInquiries(int pageNumber,String search ,String dateFilter, String orderBy,String searchValue);
     int countWriter(String search ,String dateFilter, String orderBy,String searchValue);

//   문의 게시글 삭제
     void deleteInquiriy(List<Integer> inquiryId);
}
