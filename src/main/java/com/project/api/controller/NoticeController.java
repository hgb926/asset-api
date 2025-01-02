package com.project.api.controller;

import com.project.api.dto.request.NoticeSaveDto;
import com.project.api.dto.response.NoticeResponseDto;
import com.project.api.entity.Notice;
import com.project.api.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notice")
@Slf4j
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping
    public ResponseEntity<?> addNotice(@RequestBody NoticeSaveDto dto) {
        log.info("notice save dto: {}", dto);
        Notice notice = noticeService.addNotice(dto);
        return ResponseEntity.ok().body(notice);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> findNoticeList(@PathVariable Long userId) {
        log.info("userID = {} ", userId);
        List<NoticeResponseDto> noticeList = noticeService.findNoticeList(userId)
                .stream()
                .map(NoticeResponseDto::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(noticeList);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> clickEvent(@PathVariable Long id) {
        log.info("notice id = {} ", id);
        noticeService.clickEvent(id);
        return ResponseEntity.ok().body("success");
    }

    @PostMapping("/all/{id}")
    public ResponseEntity<?> clickAllEvent(@PathVariable Long id) {
        log.info("notice id = {} ", id);
        noticeService.clickAllEvent(id);
        return ResponseEntity.ok().body("success");
    }

}
