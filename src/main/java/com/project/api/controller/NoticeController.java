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

/**
 * NoticeController
 *
 * 실시간 알림 기능을 제공하는 컨트롤러입니다.
 * - Server-Sent Events(SSE) 방식으로 클라이언트에게 실시간 알림을 전달합니다.
 * - 새로운 알림이 발생하면 SSE를 통해 클라이언트에 데이터를 전송합니다.
 */
@RestController
@RequestMapping("/notice")
@Slf4j
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    /**
     * CopyOnWriteArrayList: 멀티스레드 환경에서 안전하게 사용 가능한 리스트
     * - 각 클라이언트의 SSEEmitter 객체를 저장합니다.
     */
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    /**
     * SSE 구독 엔드포인트
     *
     * 클라이언트가 `/notice/subscribe` 엔드포인트로 연결하면 서버는 SseEmitter를 반환합니다.
     * 이 연결은 클라이언트가 실시간 알림을 받을 수 있게 합니다.
     *
     * @return SseEmitter - SSE 연결 객체
     */
    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe() {
        // 타임아웃: 1000ms (1초)
        // 실시간 알림 시스템에서는 적절한 타임아웃을 설정하는 것이 중요합니다.
        SseEmitter emitter = new SseEmitter(1000L);

        // 새로 생성된 emitter를 리스트에 추가
        emitters.add(emitter);

        // 연결 종료 또는 타임아웃 시 emitter 제거
        emitter.onCompletion(() -> {
            emitters.remove(emitter);
            log.info("Emitter completed and removed.");
        });

        emitter.onTimeout(() -> {
            emitters.remove(emitter);
            log.info("Emitter timed out and removed.");
        });

        return emitter;
    }

    /**
     * 알림 전송 엔드포인트
     *
     * 클라이언트가 `/notice/send` 엔드포인트로 POST 요청을 보내면 새로운 알림을 저장하고
     * 모든 구독된 클라이언트에게 알림을 전송합니다.
     *
     * @param dto NoticeSaveDto - 알림 데이터 전송 객체
     * @return String - 알림 전송 성공 메시지
     */
    @PostMapping("/send")
    public String sendNotice(@RequestBody NoticeSaveDto dto) {
        // Notice 객체 저장
        Notice notice = noticeService.addNotice(dto);

        // 모든 SSE 구독자에게 알림 데이터 전송
        emitters.forEach(emitter -> {
            try {
                // 알림 데이터 전송
                emitter.send(SseEmitter.event()
                        .name("notice") // 이벤트 이름
                        .data(notice)); // 데이터

            } catch (IOException e) {
                // 전송 실패 시 emitter 제거
                emitter.complete();
                emitters.remove(emitter);
                log.warn("Emitter removed due to IOException", e);
            }
        });

        return "Notice sent successfully";
    }
}