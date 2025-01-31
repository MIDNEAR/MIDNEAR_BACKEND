package com.midnear.midnearshopping.domain.dto.Inquiries;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
@Getter
@Setter
public class UserInquiryListDto {
    private Long inquiryId;
    private String title;
    private Date createdAt;
    private Long viewCount;
    private int isPrivate;
    private String hasReply;
    private String id;
}
