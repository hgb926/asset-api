package com.project.api.controller;

import com.project.api.dto.request.NoticeSaveDto;
import com.project.api.entity.Notice;
import com.project.api.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notice")
@Slf4j
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    // StackOverFlow 문제
    @PostMapping
    public ResponseEntity<?> addNotice(@RequestBody NoticeSaveDto dto) {
        log.info("notice save dto: {}", dto);
        Notice notice = noticeService.addNotice(dto);
        return ResponseEntity.ok().body(notice);
    }
}
