package com.midnear.midnearshopping.controller;

import com.midnear.midnearshopping.domain.dto.notice.NextNoticeDto;
import com.midnear.midnearshopping.domain.dto.notice.NoticeDto;
import com.midnear.midnearshopping.domain.dto.notice.NoticeListDto;
import com.midnear.midnearshopping.exception.ApiResponse;
import com.midnear.midnearshopping.service.notice.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {
    private final NoticeService noticeService;

    // 세부 공지사항 조회
    @GetMapping("/{noticeId}")
    public ResponseEntity<ApiResponse> getNotice(@PathVariable("noticeId")  Long noticeId) {
        try {
            NoticeDto notice = noticeService.getNotice(noticeId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "세부 공지사항 불러오기 성공.", notice));
        } catch(IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "공지사항을 찾을 수 없습니다.", null));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 다음글 정보 가져오기
    @GetMapping("/next/{noticeId}")
    public ResponseEntity<ApiResponse> getNextNotice(@PathVariable("noticeId")  Long noticeId) {
        try {
            NextNoticeDto notice = noticeService.getNextNotice(noticeId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "다음 공지사항 불러오기 성공.", notice));
        } catch(IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "공지사항을 찾을 수 없습니다.", null));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 고정글 목록 조회
    @GetMapping("/fixed")
    public ResponseEntity<ApiResponse> getNoticeFixedList(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "dateRange", defaultValue = "전체") String dateRange,
            @RequestParam(name = "searchText", required = false) String searchText
    ) {
        try {
            List<NoticeListDto> noticeList = noticeService.getNoticeFixedList();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "공지사항 고정글 목록 조회 성공.", noticeList));
        }  catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 일반글 목록 조회( 10개로 페이징 )
    @GetMapping("/unfixed")
    public ResponseEntity<ApiResponse> getNoticeList(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "dateRange", defaultValue = "전체") String dateRange,
            @RequestParam(name = "searchText", required = false) String searchText
    ) {
        try {
            List<NoticeListDto> noticeList = noticeService.getNoticeList(page, dateRange, searchText);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, " 공지사항 일반글 목록 조회 성공.", noticeList));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }
}
