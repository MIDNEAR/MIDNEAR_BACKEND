package com.midnear.midnearshopping.mapper;

import com.midnear.midnearshopping.domain.dto.InquiriesDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InquiriesMapper {
   InquiriesDTO selectInquiries(Long inquiry_id);
}
