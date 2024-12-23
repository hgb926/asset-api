package com.project.api.controller;

import com.project.api.dto.request.NoticeSaveDto;
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

@RestController
@RequestMapping("/notice")
@Slf4j
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping
    public ResponseEntity<?> addNotice(NoticeSaveDto dto) {
        log.info("notice save dto: {}", dto);
        Notice notice = noticeService.addNotice(dto);
        return ResponseEntity.ok().body(notice);
    }

}
