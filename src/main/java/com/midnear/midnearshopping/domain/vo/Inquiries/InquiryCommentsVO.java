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
public class InquiryCommentsVO {
    private Long commentId;
    private String replyContent;
    private Date replyDate;
    private Long inquiryId;
}
