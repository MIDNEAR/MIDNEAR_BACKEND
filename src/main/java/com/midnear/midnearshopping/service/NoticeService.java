package com.midnear.midnearshopping.service;

import com.midnear.midnearshopping.domain.dto.notice.NoticeDto;
import com.midnear.midnearshopping.domain.vo.NoticeVo;
import com.midnear.midnearshopping.mapper.notice.NoticeMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class NoticeService {
    private final NoticeMapper noticeMapper;

    @Transactional
    public void writeNotice(NoticeDto noticeDto) {
        NoticeVo noticeVo = NoticeVo.toEntity(noticeDto);
        noticeMapper.createNotice(noticeVo);
    }

    public NoticeDto getNotice(int noticeId) {
        return NoticeDto.toDto(noticeMapper.findNoticeById(noticeId));
    }

    @Transactional
    public void modifyNotice(NoticeDto noticeDto) throws NotFoundException {
        if (noticeMapper.findNoticeById(noticeDto.getNoticeId()) == null) {
            throw new NotFoundException("수정할 공지사항을 찾을 수 없습니다.");
        }
        NoticeVo noticeVo = NoticeVo.toEntity(noticeDto);
        noticeMapper.updateNotice(noticeVo);
    }

    @Transactional
    public void deleteNotice(List<Integer> deleteList) {
        noticeMapper.deleteNotice(deleteList);
    }

    public List<NoticeDto> getFixedNoticeList() {
        return noticeMapper.getFixedNotices()
                .stream()
                .map(NoticeDto::toDto)
                .collect(Collectors.toList());
    }

    public List<NoticeDto> getNoticeList() {
        return noticeMapper.getNotices()
                .stream()
                .map(NoticeDto::toDto)
                .collect(Collectors.toList());
    }

}
