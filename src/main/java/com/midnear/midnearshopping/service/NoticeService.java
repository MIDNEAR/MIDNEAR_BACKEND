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
import software.amazon.awssdk.services.s3.model.S3Exception;

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
                s3Service.deleteFile(file.getFileUrl());
            }
            e.printStackTrace();
            throw new RuntimeException("이미지 정보를 데이터베이스에 저장하는 중 오류가 발생했습니다.", e);
        }
    }

    public NoticeDto getNotice(Long noticeId) {
        return NoticeDto.toDto(noticeMapper.findNoticeById(noticeId));
    }

    @Transactional
    public void modifyNotice(NoticeDto noticeDto) throws NotFoundException {
        if (noticeMapper.findNoticeById(noticeDto.getNoticeId()) == null) {
            throw new NotFoundException("수정할 공지사항을 찾을 수 없습니다.");
        }
        List<FileDto> fileInfo = null;
        try {
            // 공지사항 테이블 수정
            NoticeVo noticeVo = NoticeVo.toEntity(noticeDto);
            noticeMapper.updateNotice(noticeVo);

            // 기존 이미지 삭제
            List<NoticeImagesVo> noticeImagesVoList = noticeImagesMapper.getNoticeImageVo(noticeDto.getNoticeId());
            for (NoticeImagesVo imagesVo : noticeImagesVoList) {
                s3Service.deleteFile(imagesVo.getImageUrl());
            }

            // 새로운 파일 업로드
            fileInfo = s3Service.uploadFiles("notices", noticeDto.getFiles());

            // DB에 이미지 정보 변경
            noticeImagesMapper.deleteNoticeImages(noticeDto.getNoticeId());
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

        } catch (S3Exception s3Ex) {
            throw new RuntimeException("S3 처리 중 오류가 발생했습니다.", s3Ex);

            // 예외 처리를 어느 정도로 자세하게 하면 좋을까요..,~ 커스텀은 코드 재활용성이 떨어져보임.,
//        } catch (DatabaseException dbEx) {
//            // S3에 업로드된 파일 삭제
//            if (fileInfo != null) {
//                for (FileDto file : fileInfo) {
//                    s3Service.deleteFile(file.getFileUrl());
//                }
//            }
//            throw new RuntimeException("DB 처리 중 오류가 발생했습니다.", dbEx);
//
          } catch (Exception ex) {
            if (fileInfo != null) {
                 for (FileDto file : fileInfo) {
                     s3Service.deleteFile(file.getFileUrl());
                 }
            }
            throw new RuntimeException("DB 업데이트 중 오류가 발생했습니다", ex);
        }
    }

    @Transactional
    public void deleteNotices(List<Long> deleteList) {
        noticeMapper.deleteNotices(deleteList);
        // 관련 이미지 삭제
        for (Long id : deleteList) {
            // 버킷에서 삭제
            List<NoticeImagesVo> noticeImagesVoList = noticeImagesMapper.getNoticeImageVo(id);
            for (NoticeImagesVo imagesVo : noticeImagesVoList) {
                s3Service.deleteFile(imagesVo.getImageUrl());
            }
            // 테이블에서 이미지 정보 삭제
            noticeImagesMapper.deleteNoticeImages(id);
        }
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
    public void fixNotices(List<Long> fixList) {
        if (fixList == null || fixList.isEmpty()) {
            throw new IllegalArgumentException("선택된 공지사항이 없습니다.");
        }
        noticeMapper.fixNotices(fixList);
    }

    @Transactional
    public void unfixNotices(List<Long> unfixList) {
        if (unfixList == null || unfixList.isEmpty()) {
            throw new IllegalArgumentException("선택된 공지사항이 없습니다.");
        }
        noticeMapper.unfixNotices(unfixList);
    }

}
