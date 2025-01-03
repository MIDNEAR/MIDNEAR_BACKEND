package com.midnear.midnearshopping.mapper.notice;

import com.midnear.midnearshopping.domain.vo.NoticeVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper {
    void createNotice(NoticeVo noticeVo);
    NoticeVo findNoticeById(int noticeId);
    void updateNotice(NoticeVo noticeVo);
    void deleteNotice(List<Integer> noticeList);
    List<NoticeVo> getFixedNotices();
    List<NoticeVo> getNotices();
}
