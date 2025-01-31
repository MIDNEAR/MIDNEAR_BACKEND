package com.midnear.midnearshopping.domain.vo.Inquiries;

import com.midnear.midnearshopping.domain.dto.Inquiries.InquiryRequestDto;
import lombok.*;
import org.springframework.stereotype.Component;
import java.sql.Date;
import java.time.LocalDate;

@Getter
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

    public static InquiriesVO toEntity(InquiryRequestDto inquiryRequestDto) {
        return InquiriesVO.builder()
                .title(inquiryRequestDto.getTitle())
                .content(inquiryRequestDto.getContent())
                .isPrivate(inquiryRequestDto.getIsPrivate())
                .viewCount(0L) // 신규 생성 시 조회 수 0으로 설정
                .hasReply("대기중")
                .userId(inquiryRequestDto.getUserId())
                .build();
    }
}
