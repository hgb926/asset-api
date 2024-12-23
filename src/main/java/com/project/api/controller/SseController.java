package com.project.api.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@RestController
public class SseController {

    @GetMapping(value = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamSseMvc() throws Exception {
        SseEmitter emitter = new SseEmitter();

        // 새로운 Thread로 실행
        new Thread(() -> {
            try {
                for (int i = 0; i < 3; i++) {
                    emitter.send(SseEmitter.event().data("Sse event! " + System.currentTimeMillis()));
                    Thread.sleep(1000);  // 1초 대기
                }
                emitter.complete();
            } catch (IOException | InterruptedException e) {
                emitter.completeWithError(e);
            }
        }).start();

        return emitter;
    }
}
