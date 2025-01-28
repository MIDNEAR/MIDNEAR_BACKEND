package com.midnear.midnearshopping.controller;

import com.midnear.midnearshopping.domain.dto.notice.NoticeDto;
import com.midnear.midnearshopping.domain.vo.notice.NoticeVo;
import com.midnear.midnearshopping.exception.ApiResponse;
import com.midnear.midnearshopping.service.NoticeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {
    private final NoticeService noticeService;

    // 공지사항 작성
    @PostMapping("/write")
    public ResponseEntity<ApiResponse> writeNotice(
            @ModelAttribute @Valid NoticeDto noticeDto) {
        try {
            noticeService.writeNotice(noticeDto);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "작성이 완료되었습니다.", null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, ex.getMessage(), null));
        }
    }

    // 수정할 공지사항 데이터 불러오기
    @GetMapping("/modify/{noticeId}")
    public ResponseEntity<ApiResponse> getNotice(@PathVariable("noticeId")  Long noticeId) {
        try {
            NoticeDto notice = noticeService.getNotice(noticeId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "수정할 공지사항 불러오기 성공.", notice));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 공지사항 수정
    @PutMapping("/modify")
    public ResponseEntity<ApiResponse> modifyNotice(@ModelAttribute @Valid NoticeDto noticeDto) {
        try {
            noticeService.modifyNotice(noticeDto);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "수정이 완료되었습니다.", null));
        } catch (NotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, ex.getMessage(), null));
        } catch (S3Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, ex.getMessage(), null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 공지사항 삭제
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteNotices(@RequestBody List<Long> deleteList) {
        try {
            noticeService.deleteNotices(deleteList);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "삭제가 완료되었습니다.", null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 고정 글 불러오기
    @GetMapping("/fixed")
    public ResponseEntity<ApiResponse> getFixedNoticeList() {
        try {
            List<NoticeDto> fixedNoticeList = noticeService.getFixedNoticeList();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "데이터 불러오기 성공.", fixedNoticeList));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 일반 글 불러오기
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getNoticeList(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "23") int size,
            @RequestParam(name = "sortOrder", defaultValue = "최신순") String sortOrder,
            @RequestParam(name = "dateRange", defaultValue = "전체") String dateRange,
            @RequestParam(name = "searchRange", required = false) String searchRange, // search 범위 없으면 검색 x
            @RequestParam(name = "searchText", defaultValue = "") String searchText
    ) {
        try {
            // List<noticeDto> + 전체 페이지 수
            Map<String, Object> response = noticeService.getNoticeList(page, size, sortOrder, dateRange, searchRange, searchText);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "데이터 불러오기 성공.", response));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 고정 글 설정
    @PutMapping("/fix")
    public ResponseEntity<ApiResponse> fixNotices(@RequestBody List<Long> fixList) {
        try {
            noticeService.fixNotices(fixList);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "선택 글 고정에 성공하였습니다.", null));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, ex.getMessage(), null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 고정글 설정 해제
    @PutMapping("/unfix")
    public ResponseEntity<ApiResponse> unfixNotices(@RequestBody List<Long> unfixList) {
        try {
            noticeService.unfixNotices(unfixList);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "선택 글 고정을 해제하였습니다.", null));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, ex.getMessage(), null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }
}
