package com.project.api.service;

import com.project.api.dto.request.NoticeSaveDto;
import com.project.api.entity.Notice;
import com.project.api.entity.User;
import com.project.api.repository.NoticeRepository;
import com.project.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class NoticeService {
    private final UserRepository userRepository;
    private final NoticeRepository noticeRepository;

    public Notice addNotice(NoticeSaveDto dto) {
        try {
            User foundUser = userRepository.findById(dto.getUserId()).orElseThrow(null);
            Notice newNotice = Notice.builder()
                    .user(foundUser)
                    .message(dto.getMessage())
                    .isClicked(false)
                    .build();
            noticeRepository.save(newNotice);
            foundUser.getNoticeList().add(newNotice);
            log.info("foundUser.getNoticeList() {}" , foundUser.getNoticeList());
            return newNotice;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
