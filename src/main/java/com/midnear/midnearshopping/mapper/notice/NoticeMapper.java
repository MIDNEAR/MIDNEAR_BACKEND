package com.midnear.midnearshopping.mapper.notice;

import com.midnear.midnearshopping.domain.vo.notice.NoticeVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper {
    void createNotice(NoticeVo noticeVo);
    NoticeVo findNoticeById(Long noticeId);
    void updateNotice(NoticeVo noticeVo);
    void deleteNotices(List<Long> noticeList);
    List<NoticeVo> getFixedNotices();
    List<NoticeVo> getNotices();
    void fixNotices(List<Long> noticeList);
    void unfixNotices(List<Long> noticeList);

}
