package com.midnear.midnearshopping.domain.dto.notice;

import com.midnear.midnearshopping.domain.vo.notice.NoticeImagesVo;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeImagesDto {
    private Long noticeImageId;

    private String imageUrl;

    private Long fileSize;

    private String extension;

    private Date imageCreationDate;

    private Long noticeId;
    public static NoticeImagesDto toDto(NoticeImagesVo noticeImagesVo) {
        return NoticeImagesDto.builder()
                .noticeImageId(noticeImagesVo.getNoticeImageId())
                .imageUrl(noticeImagesVo.getImageUrl())
                .fileSize(noticeImagesVo.getFileSize())
                .extension(noticeImagesVo.getExtension())
                .imageCreationDate(noticeImagesVo.getImageCreationDate())
                .noticeId(noticeImagesVo.getNoticeId())
                .build();
    }
}
