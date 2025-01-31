package com.midnear.midnearshopping.mapper.Inquiries;

import com.midnear.midnearshopping.domain.dto.Inquiries.InquiriesDTO;
import com.midnear.midnearshopping.domain.dto.Inquiries.InquiriesListDTO;
import com.midnear.midnearshopping.domain.dto.Inquiries.UserInquiryListDto;
import com.midnear.midnearshopping.domain.vo.Inquiries.InquiriesVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserInquiryMapper {
    InquiriesDTO selectInquiries(Long inquiryId);
    void insertInquiry(InquiriesVO inquiriesVO);
    List<UserInquiryListDto> SelectInquirylist(@Param("offset") int offset, @Param("pageSize") int pageSize);
    void updateViewCount(Long inquiryId);
}
