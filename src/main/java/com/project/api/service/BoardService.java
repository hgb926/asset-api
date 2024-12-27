package com.project.api.service;

import com.project.api.dto.request.BoardSaveDto;
import com.project.api.dto.response.BoardResponseDto;
import com.project.api.entity.Board;
import com.project.api.entity.User;
import com.project.api.repository.BoardRepository;
import com.project.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public Board saveBoard(BoardSaveDto dto) {
        if (dto.getTitle() == null || dto.getContent() == null) {
            return null;
        }
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


    public List<Board> getBoards() {
        List<Board> boardList = boardRepository.findAll();

        Collections.reverse(boardList);
        return boardList;

    }
}
