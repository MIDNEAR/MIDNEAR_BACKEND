package com.midnear.midnearshopping.mapper.notice;

import com.midnear.midnearshopping.domain.dto.notice.NoticeImagesDto;
import com.midnear.midnearshopping.domain.dto.notice.PopupDto;
import com.midnear.midnearshopping.domain.vo.notice.NoticeImagesVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeImagesMapper {
    void uploadNoticeImages(NoticeImagesVo noticeImagesVo);
    NoticeImagesVo getNoticeImageVo(Long noticeId);
    void deleteNoticeImages(Long noticeId);
    List<PopupDto> getPopupImages();
}
