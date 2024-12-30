package com.midnear.midnearshopping.domain.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class Inquiry_commentsDTO {
    private Long commentId;
    private String replyContent;
    private Date replyDate;
    private Date replyModified_date;
    private Long inquiryId;
}
