package com.midnear.midnearshopping.domain.vo.notice;

import com.midnear.midnearshopping.domain.dto.notice.NoticeDto;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeVo {

    private Integer noticeId;

    private String title;

    private String content;

    private Date createdDate;

    private Boolean fix;

    private List<MultipartFile> files;

    public static NoticeVo toEntity(NoticeDto noticeDto) {
        return NoticeVo.builder()
                .noticeId(noticeDto.getNoticeId())
                .title(noticeDto.getTitle())
                .content(noticeDto.getContent())
                .createdDate(noticeDto.getCreatedDate())
                .fix(noticeDto.getFix())
                .files(noticeDto.getFiles())
                .build();
    }

}
