package com.midnear.midnearshopping.domain.dto.Inquiries;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Date;
@Component
@Data
public class InquiriesListDTO {
    private Long inquiryId;
    private String title;
    private String content;
    private Date createdAt;
    private Long viewCount;
    private String hasReply;
    private String id;
}
