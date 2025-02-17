package com.midnear.midnearshopping.controller;

import com.midnear.midnearshopping.domain.dto.notice.NoticeDto;
import com.midnear.midnearshopping.exception.ApiResponse;
import com.midnear.midnearshopping.service.notice.NoticeManagementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/noticeManagement")
@Slf4j
public class NoticeManagementController {
    private final NoticeManagementService noticeManagementService;

    private boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(authentication.getName());
        return authentication != null && "admin".equals(authentication.getName());
    }

    // 공지사항 작성
    @PostMapping("/write")
    public ResponseEntity<ApiResponse> writeNotice(
            @ModelAttribute @Valid NoticeDto noticeDto) {
        try {
            if (!isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
            }
            noticeManagementService.writeNotice(noticeDto);
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
            if (!isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
            }
            NoticeDto notice = noticeManagementService.getNotice(noticeId);
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
            if (!isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
            }
            noticeManagementService.modifyNotice(noticeDto);
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
            if (!isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
            }
            noticeManagementService.deleteNotices(deleteList);
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
            if (!isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
            }
            List<NoticeDto> fixedNoticeList = noticeManagementService.getFixedNoticeList();
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
            @RequestParam(name = "sortOrder", defaultValue = "최신순") String sortOrder,
            @RequestParam(name = "dateRange", defaultValue = "전체") String dateRange,
            @RequestParam(name = "searchRange", defaultValue = "") String searchRange, // search 범위 없으면 검색 x
            @RequestParam(name = "searchText", required = false) String searchText
    ) {
        try {
            if (!isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
            }
            // List<noticeDto> + 전체 페이지 수
            Map<String, Object> response = noticeManagementService.getNoticeList(page, sortOrder, dateRange, searchRange, searchText);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "데이터 불러오기 성공.", response));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 고정 글 설정
    @PatchMapping("/fix")
    public ResponseEntity<ApiResponse> fixNotices(@RequestBody List<Long> fixList) {
        try {
            if (!isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
            }
            noticeManagementService.fixNotices(fixList);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "선택 글 고정에 성공하였습니다.", null));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "선택된 공지사항이 없습니다.", null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }

    // 고정글 설정 해제
    @PatchMapping("/unfix")
    public ResponseEntity<ApiResponse> unfixNotices(@RequestBody List<Long> unfixList) {
        try {
            if (!isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse(false, "관리자만 접근 가능합니다.", null));
            }
            noticeManagementService.unfixNotices(unfixList);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(true, "선택 글 고정을 해제하였습니다.", null));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "선택된 공지사항이 없습니다.", null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다.", null));
        }
    }
}
