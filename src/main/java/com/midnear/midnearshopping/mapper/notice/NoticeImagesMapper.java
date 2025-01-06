package com.midnear.midnearshopping.mapper.notice;

import com.midnear.midnearshopping.domain.dto.notice.NoticeImagesDto;
import com.midnear.midnearshopping.domain.vo.notice.NoticeImagesVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NoticeImagesMapper {
    void uploadNoticeImages(NoticeImagesVo noticeImagesVo);
}
