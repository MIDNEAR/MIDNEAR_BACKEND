package com.midnear.midnearshopping.domain.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
@Data
public class Inquiry_commentsDTO {
    private Long commentId;
    private String replyContent;
    private Date replyDate;
    private Date replyModifiedDate;
    private Long inquiryId;
}
