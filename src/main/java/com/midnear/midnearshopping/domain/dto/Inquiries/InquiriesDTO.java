package com.midnear.midnearshopping.domain.dto.Inquiries;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Data
public class InquiriesDTO {
    private Long inquiryId;
    private String title;
    private String content;
    private Date createdAt;
    private Long isPrivate;
    private Long viewCount;
    private String hasReply;
    private Long userId;
    private String id;
    private Long commentId;
    private String replyContent;
    private Date replyDate;
}
