package com.midnear.midnearshopping.service;

import com.midnear.midnearshopping.domain.dto.FileDto;
import com.midnear.midnearshopping.domain.dto.notice.NoticeDto;
import com.midnear.midnearshopping.domain.vo.notice.NoticeImagesVo;
import com.midnear.midnearshopping.domain.vo.notice.NoticeVo;
import com.midnear.midnearshopping.mapper.notice.NoticeImagesMapper;
import com.midnear.midnearshopping.mapper.notice.NoticeMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class NoticeService {
    private final NoticeMapper noticeMapper;
    private final NoticeImagesMapper noticeImagesMapper;
    private final S3Service s3Service;

    @Transactional
    public void writeNotice(NoticeDto noticeDto) {
        NoticeVo noticeVo = NoticeVo.toEntity(noticeDto);
        noticeMapper.createNotice(noticeVo); // 쿼리 실행 후 vo에 noticeId(pk) 저장

        //버킷에 저장
        List<FileDto> fileInfo;
        try {
            fileInfo = s3Service.uploadFiles("notices", noticeDto.getFiles());
        } catch (Exception e) {
            throw new RuntimeException("파일 업로드 중 오류가 발생했습니다.", e);
        }

        //DB에 이미지 정보 저장
        try {
            for (FileDto file : fileInfo) {
                NoticeImagesVo imagesVo = NoticeImagesVo.builder()
                        .noticeImageId(null)
                        .imageUrl(file.getFileUrl())
                        .fileSize(file.getFileSize())
                        .extension(file.getExtension())
                        .imageCreationDate(null)
                        .noticeId(noticeVo.getNoticeId())
                        .build();
                noticeImagesMapper.uploadNoticeImages(imagesVo);
            }

        } catch (Exception e) {
            // S3에 이미 업로드된 파일 삭제
            for (FileDto file : fileInfo) {
                //s3Service.deleteFile(file.getFileUrl());
            }
            e.printStackTrace();
            throw new RuntimeException("이미지 정보를 데이터베이스에 저장하는 중 오류가 발생했습니다.", e);
        }
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
    public void deleteNotices(List<Integer> deleteList) {
        noticeMapper.deleteNotices(deleteList);
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

    @Transactional
    public void fixNotices(List<Integer> fixList) {
        if (fixList == null || fixList.isEmpty()) {
            throw new IllegalArgumentException("선택된 공지사항이 없습니다.");
        }
        noticeMapper.fixNotices(fixList);
    }

    @Transactional
    public void unfixNotices(List<Integer> unfixList) {
        if (unfixList == null || unfixList.isEmpty()) {
            throw new IllegalArgumentException("선택된 공지사항이 없습니다.");
        }
        noticeMapper.unfixNotices(unfixList);
    }

}
