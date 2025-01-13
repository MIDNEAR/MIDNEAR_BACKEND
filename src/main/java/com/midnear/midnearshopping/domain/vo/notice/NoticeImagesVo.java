package com.midnear.midnearshopping.domain.vo.notice;

import com.midnear.midnearshopping.domain.dto.notice.NoticeImagesDto;
import lombok.*;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeImagesVo {
    private Long noticeImageId;

    private String imageUrl;

    private Long fileSize;

    private String extension;

    private Date imageCreationDate;

    private Long noticeId;
    public static NoticeImagesVo toEntity(NoticeImagesDto noticeImagesDto) {
        return NoticeImagesVo.builder()
                .noticeImageId(noticeImagesDto.getNoticeImageId())
                .imageUrl(noticeImagesDto.getImageUrl())
                .fileSize(noticeImagesDto.getFileSize())
                .extension(noticeImagesDto.getExtension())
                .imageCreationDate(noticeImagesDto.getImageCreationDate())
                .noticeId(noticeImagesDto.getNoticeId())
                .build();
    }

}
