package com.midnear.midnearshopping.mapper.notice;

import com.midnear.midnearshopping.domain.vo.notice.NoticeVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper {
    void createNotice(NoticeVo noticeVo);
    NoticeVo findNoticeById(int noticeId);
    void updateNotice(NoticeVo noticeVo);
    void deleteNotices(List<Integer> noticeList);
    List<NoticeVo> getFixedNotices();
    List<NoticeVo> getNotices();
    void fixNotices(List<Integer> noticeList);
    void unfixNotices(List<Integer> noticeList);

}
