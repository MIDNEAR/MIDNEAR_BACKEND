package com.midnear.midnearshopping.domain.vo;

import com.midnear.midnearshopping.domain.dto.notice.NoticeDto;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeVo {

    private Integer noticeId;

    private String title;

    private String content;

    private Date createdDate;

    private int popupStatus;

    private Boolean fix;

    public static NoticeVo toEntity(NoticeDto noticeDto) {
        return NoticeVo.builder()
                .noticeId(noticeDto.getNoticeId())
                .title(noticeDto.getTitle())
                .content(noticeDto.getContent())
                .createdDate(noticeDto.getCreatedDate())
                .popupStatus(noticeDto.getPopupStatus())
                .fix(noticeDto.getFix())
                .build();
    }

}
