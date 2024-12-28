package com.project.api.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class SseService {
    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    /**
     * 사용자별 SSE 연결 생성
     */
    public SseEmitter connect(Long userId) {
        SseEmitter emitter = new SseEmitter(0L); // 무제한 대기
        emitters.put(userId, emitter);

        emitter.onCompletion(() -> emitters.remove(userId));
        emitter.onTimeout(() -> emitters.remove(userId));
        emitter.onError((e) -> emitters.remove(userId));

        return emitter;
    }

    /**
     * 특정 사용자에게 알림 전송
     */
    public void sendNotice(Long userId, String message, String type, Long boardId, Long goalId) {
        SseEmitter emitter = emitters.get(userId);
        log.info("userId {}, message {}, type {}, boardId {}, goalId {} ", userId, message, type, boardId, goalId);
        if (emitter != null) {
            try {
                Map<String, Object> data = Map.of(
                        "message", message,
                        "type", type,
                        "userId", userId,
                        "boardId", boardId,
                        "goalId", goalId
                );

                emitter.send(SseEmitter.event()
                        .name("notice")
                        .data(data));
            } catch (IOException e) {
                emitters.remove(userId);
            }
        }
    }
}