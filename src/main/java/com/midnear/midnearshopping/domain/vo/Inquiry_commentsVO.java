package com.midnear.midnearshopping.domain.vo;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inquiry_commentsVO {
    private Long commentId;
    private String replyContent;
    private Date replyDate;
    private Date replyModifiedDate;
    private Long inquiryId;
}
