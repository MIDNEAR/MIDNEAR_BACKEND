package com.midnear.midnearshopping.mapper.Inquiries;

import com.midnear.midnearshopping.domain.vo.Inquiries.InquiriesVO;
import org.apache.ibatis.annotations.Mapper;



@Mapper
public interface UserInquiryMapper {
    void insertInquiry(InquiriesVO inquiriesVO);
}
