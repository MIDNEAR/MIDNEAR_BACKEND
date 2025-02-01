package com.midnear.midnearshopping.domain.dto.notice;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NextNoticeDto {
    private Long noticeId;
    private String title;
}
