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
public class InquiriesVO {
    private Long inquiryId;
    private String title;
    private String content;
    private Date createdAt;
    private Date updatedAt;
    private Long isPrivate;
    private Long viewCount;
    private String hasReply;
    private Long userId;

}
