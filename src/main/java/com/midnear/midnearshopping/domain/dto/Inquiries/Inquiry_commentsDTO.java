package com.midnear.midnearshopping.domain.dto.Inquiries;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Data
public class Inquiry_commentsDTO {
    private Long commentId;
    private String replyContent;
    private Date replyDate;
    private Long inquiryId;
}
