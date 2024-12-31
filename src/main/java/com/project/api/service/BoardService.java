package com.project.api.service;

import com.project.api.dto.request.BoardModifyDto;
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
import org.springframework.data.domain.Sort;
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

    /**
     * 페이징된 게시글 목록 반환
     * @param page 페이지 번호 (0부터 시작)
     * @param size 페이지당 항목 수
     * @return Page<BoardResponseDto>
     */
    public Page<BoardResponseDto> getBoards(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Board> boardPage = boardRepository.findAll(pageable);

        return boardPage.map(BoardResponseDto::new);
    }

    public BoardResponseDto findOne(Long boardId) {
        Board foundBoard = boardRepository.findById(boardId).orElseThrow(null);
        foundBoard.setViewCount(foundBoard.getViewCount() + 1);
        boardRepository.save(foundBoard);
        BoardResponseDto dto = new BoardResponseDto(foundBoard);
        log.info("converted board dto : {}", dto);
        return dto;
    }

    public BoardResponseDto modifyBoard(BoardModifyDto dto) {
        Board foundBoard = boardRepository.findById(dto.getId()).orElseThrow();
        foundBoard.setTitle(dto.getTitle());
        foundBoard.setContent(dto.getContent());
        foundBoard.setCategory(dto.getCategory());
        foundBoard.setModified(true);
        boardRepository.save(foundBoard);
        BoardResponseDto modifiedBoard = new BoardResponseDto(foundBoard);
        return modifiedBoard;
    }
}
