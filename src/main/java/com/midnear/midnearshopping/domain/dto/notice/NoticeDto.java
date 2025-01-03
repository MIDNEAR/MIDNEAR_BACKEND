package com.midnear.midnearshopping.domain.dto.notice;

import com.midnear.midnearshopping.domain.vo.NoticeVo;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeDto {
    private Integer noticeId;

    private String title;

    private String content;

    private Date createdDate;

    private int popupStatus;

    private Boolean fix;

    //NOT NULL이 안 걸려있어서 제목이나 내용이 NULL이 들어갈 수 있는데
    //이떄 기본값으로 "No content"를 넣을까요,,?
    public static NoticeDto toDto(NoticeVo notice) {
        return NoticeDto.builder()
                .noticeId(notice.getNoticeId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .createdDate(notice.getCreatedDate())
                .popupStatus(notice.getPopupStatus())
                .fix(notice.getFix())
                .build();
    }
}
