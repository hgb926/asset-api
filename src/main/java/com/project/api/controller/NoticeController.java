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

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/notice")
@Slf4j
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    // Server sent event
    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(1000L);// 연결 시간 무제한
        emitters.add(emitter);

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));

        return emitter;
    }

    @PostMapping("/send")
    public String sendNotice(@RequestBody NoticeSaveDto dto) {
        Notice notice = noticeService.addNotice(dto);
        emitters.forEach(emitter -> {
            try {
                emitter.send(SseEmitter.event()
                        .name("notice")
                        .data(notice));
            } catch (IOException e) {
                emitter.complete();
                emitters.remove(emitter);
            }
        });
        return "Notice send successfully";
    }

}
