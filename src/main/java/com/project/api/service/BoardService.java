package com.project.api.service;

import com.project.api.dto.request.BoardSaveDto;
import com.project.api.entity.Board;
import com.project.api.entity.User;
import com.project.api.repository.BoardRepository;
import com.project.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public Board saveBoard(BoardSaveDto dto) {
        try {
            User foundUser = userRepository.findById(dto.getUserId()).orElseThrow(null);
            Board newBoard = Board.builder()
                    .title(dto.getTitle())
                    .content(dto.getContent())
                    .category(dto.getCategory())
                    .user(foundUser)
                    .build();
            boardRepository.save(newBoard);
            return newBoard;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }


}
