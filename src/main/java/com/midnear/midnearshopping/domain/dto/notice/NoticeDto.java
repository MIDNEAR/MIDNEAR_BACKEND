package com.midnear.midnearshopping.domain.dto.notice;

import com.midnear.midnearshopping.domain.vo.notice.NoticeVo;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeDto {
    private Long noticeId;

    @NotNull(message = "제목을 입력해주세요.")
    private String title;

    @NotNull(message = "내용을 입력해주세요.")
    private String content;

    private Date createdDate;

    private Boolean fix;

    private List<MultipartFile> files;

    public static NoticeDto toDto(NoticeVo notice) {
        return NoticeDto.builder()
                .noticeId(notice.getNoticeId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .createdDate(notice.getCreatedDate())
                .fix(notice.getFix())
                .files(notice.getFiles())
                .build();
    }
}
