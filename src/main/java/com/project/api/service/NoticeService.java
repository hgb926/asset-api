package com.project.api.service;

import com.project.api.dto.request.NoticeSaveDto;
import com.project.api.entity.Notice;
import com.project.api.entity.User;
import com.project.api.repository.NoticeRepository;
import com.project.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class NoticeService {

    private final UserRepository userRepository;
    private final NoticeRepository noticeRepository;
    private final SseService sseService;

    /**
     * 알림 저장 및 SSE 전송
     */
    public Notice addNotice(NoticeSaveDto dto) {
        User foundUser = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Notice newNotice = Notice.builder()
                .user(foundUser)
                .message(dto.getMessage())
                .type(dto.getType())
                .isClicked(false)
                .boardId(dto.getBoardId())
                .goalId(dto.getGoalId())
                .build();

        noticeRepository.save(newNotice);
        foundUser.getNoticeList().add(newNotice);

        // SSE 알림 전송
        sseService.sendNotification(
                foundUser.getId(),
                dto.getMessage(),
                dto.getType().toString(),
                dto.getBoardId(),
                dto.getGoalId()
        );

        return newNotice;
    }

    /**
     * 사용자 알림 목록 조회
     */
    public List<Notice> findNoticeList(Long userId) {
        return noticeRepository.findByUserId(userId);
    }

    /**
     * 알림 클릭 이벤트 처리
     */
    public void clickEvent(Long id) {
        Notice foundNotice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notice not found"));
        foundNotice.setClicked(true);
        noticeRepository.save(foundNotice);
    }
}