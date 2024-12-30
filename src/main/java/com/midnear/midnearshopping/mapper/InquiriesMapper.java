package com.midnear.midnearshopping.mapper;

import com.midnear.midnearshopping.domain.dto.InquiriesDTO;
import com.midnear.midnearshopping.domain.dto.Inquiry_commentsDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InquiriesMapper {
   InquiriesDTO selectInquiries(Long inquiryId);
   void insertInquries(Inquiry_commentsDTO inquiryCommentsDTO);
}
