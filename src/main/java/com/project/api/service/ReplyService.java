package com.project.api.service;

import com.project.api.dto.request.ReplySaveDto;
import com.project.api.entity.Board;
import com.project.api.entity.Reply;
import com.project.api.entity.User;
import com.project.api.repository.BoardRepository;
import com.project.api.repository.ReplyRepository;
import com.project.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public Reply saveReply(ReplySaveDto dto) {
        User foundUser = userRepository.findById(dto.getUserId()).orElseThrow();
        Board foundBoard = boardRepository.findById(dto.getBoardId()).orElseThrow();

        Reply newReply = Reply.builder()
                .content(dto.getContent())
                .board(foundBoard)
                .user(foundUser)
                .build();

        replyRepository.save(newReply);

        return newReply;
    }
}
