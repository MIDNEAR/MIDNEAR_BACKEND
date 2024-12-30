package com.midnear.midnearshopping.domain.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class InquiriesDTO {
    private Long inquiryId;
    private String title;
    private String content;
    private Date createdAt;
    private Date updatedAt;
    private Long isPrivate;
    private Long viewCount;
    private String hasReply;
    private Long userId;
    private String id;
    private Long commentId;
    private String replyContent;
    private Date replyDate;
    private Date replyModified_date;
}
