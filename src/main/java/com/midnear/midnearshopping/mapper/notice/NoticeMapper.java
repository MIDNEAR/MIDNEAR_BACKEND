package com.midnear.midnearshopping.mapper.notice;

import com.midnear.midnearshopping.domain.dto.notice.NoticeListDto;
import com.midnear.midnearshopping.domain.vo.notice.NoticeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NoticeMapper {
    void createNotice(NoticeVo noticeVo);
    NoticeVo findNoticeById(Long noticeId);
    void updateNotice(NoticeVo noticeVo);
    void deleteNotices(List<Long> noticeList);
    List<NoticeVo> getFixedNotices();
    List<NoticeVo> getNotices(@Param("offset") int offset,@Param("count") int count, @Param("orderBy")String orderBy, @Param("dateRange")String dateRange, @Param("searchRange")String searchRange, @Param("searchText")String searchText);
    void fixNotices(List<Long> noticeList);
    void unfixNotices(List<Long> noticeList);
    Long count(@Param("dateRange")String dateRange, @Param("searchRange")String searchRange, @Param("searchText")String searchText);
    NoticeVo findNextFixedNotice(Long noticeId);
    NoticeVo findNextNotice(Long noticeId);
    List<NoticeListDto> getNoticesPaging(int offset, int size, String dateRange, String searchText);
}
