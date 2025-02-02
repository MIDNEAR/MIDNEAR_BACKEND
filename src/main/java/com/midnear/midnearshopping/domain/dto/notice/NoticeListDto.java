package com.midnear.midnearshopping.domain.dto.notice;

import com.midnear.midnearshopping.domain.vo.notice.NoticeVo;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeListDto {
    private Long noticeId;
    private String title;
    private Date createdDate;
    private Boolean fix;

    public static NoticeListDto toDto(NoticeVo noticeVo) {
        return NoticeListDto.builder()
                .noticeId(noticeVo.getNoticeId())
                .title(noticeVo.getTitle())
                .createdDate(noticeVo.getCreatedDate())
                .fix(noticeVo.getFix())
                .build();
    }
}
