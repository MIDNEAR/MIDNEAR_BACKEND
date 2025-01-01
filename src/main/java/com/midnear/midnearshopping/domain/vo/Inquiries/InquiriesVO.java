package com.midnear.midnearshopping.domain.vo.Inquiries;

import lombok.*;
import org.springframework.stereotype.Component;
import java.sql.Date;

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
    private Long isPrivate;
    private Long viewCount;
    private String hasReply;
    private Long userId;

}
