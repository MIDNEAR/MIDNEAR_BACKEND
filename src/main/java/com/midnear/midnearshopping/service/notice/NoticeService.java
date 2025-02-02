package com.midnear.midnearshopping.service.notice;

import com.midnear.midnearshopping.domain.dto.notice.NextNoticeDto;
import com.midnear.midnearshopping.domain.dto.notice.NoticeDto;
import com.midnear.midnearshopping.domain.dto.notice.NoticeListDto;
import com.midnear.midnearshopping.domain.vo.notice.NoticeImagesVo;
import com.midnear.midnearshopping.domain.vo.notice.NoticeVo;
import com.midnear.midnearshopping.mapper.notice.NoticeImagesMapper;
import com.midnear.midnearshopping.mapper.notice.NoticeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeMapper noticeMapper;
    private final NoticeImagesMapper noticeImagesMapper;

    public NoticeDto getNotice(Long noticeId) {
        NoticeVo noticeVo = noticeMapper.findNoticeById(noticeId);
        if (noticeVo == null) {
            throw new IllegalArgumentException();
        }
        NoticeImagesVo noticeImagesVo = noticeImagesMapper.getNoticeImageVo(noticeId);

        return NoticeDto.builder()
                .noticeId(noticeId)
                .title(noticeVo.getTitle())
                .content(noticeVo.getContent())
                .fix(noticeVo.getFix())
                .imageUrl(noticeImagesVo.getImageUrl())
                .createdDate(noticeVo.getCreatedDate())
                .build();
    }

    public NextNoticeDto getNextNotice(Long noticeId) {
        // 현재 공지 조회
        NoticeVo currentNotice = noticeMapper.findNoticeById(noticeId);
        if (currentNotice == null) {
            throw new IllegalArgumentException();
        }

        NoticeVo nextNotice;

        if (currentNotice.getFix()) {
            // 현재 공지가 고정글이라면 다음 고정글 찾기
            nextNotice = noticeMapper.findNextFixedNotice(noticeId);
            // 다음 고정글이 없으면 일반글에서 다음 공지 찾기
            if (nextNotice == null) {
                nextNotice = noticeMapper.findNextNotice(noticeId);
            }
        } else {
            // 현재 공지가 일반글이라면 다음 일반글 찾기
            nextNotice = noticeMapper.findNextNotice(noticeId);
        }

        // 다음 공지가 없는 경우
        if (nextNotice == null) {
            return null;
        }

        return NextNoticeDto.builder()
                .noticeId(nextNotice.getNoticeId())
                .title(nextNotice.getTitle())
                .build();
    }

    public List<NoticeListDto> getNoticeFixedList() {
        return noticeMapper.getFixedNotices().stream()
                .map(NoticeListDto::toDto)
                .toList();

    }

    public List<NoticeListDto> getNoticeList(int page, String dateRange, String searchText) {
        int count = getNoticeFixedList().size();
        int size = 10 - count; // 한 페이지에 보이는 공지사항 수
        int offset = (page - 1) * size;

        return noticeMapper.getNoticesPaging(offset, size, dateRange, searchText);
    }

    public List<String> getNoticePopupImages() {
        return noticeImagesMapper.getPopupImages();
    }
}
