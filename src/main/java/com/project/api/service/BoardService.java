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

import java.time.LocalDateTime;
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
     * 게시글 목록 조회 (페이징, 정렬, 검색)
     *
     * @param page      페이지 번호 (0부터 시작)
     * @param size      페이지당 항목 수
     * @param sort      정렬 방향 (asc, desc)
     * @param order     정렬 기준 (createdAt, title 등)
     * @param category  카테고리 필터 (선택사항)
     * @param keyword   검색 키워드 (선택사항)
     * @param searchType 검색 유형 (title, titleAndContent, author)
     * @return Page<BoardResponseDto>
     */
    public Page<BoardResponseDto> getBoards(int page, int size, String sort, String order,
                                            String category, String keyword, String searchType) {
        // 정렬 방향 검증
        Sort.Direction direction = "asc".equalsIgnoreCase(sort) ? Sort.Direction.ASC : Sort.Direction.DESC;

        // 정렬 기준 검증
        String sortBy = (order != null && !order.isEmpty()) ? order : "createdAt";

        log.info("Sorting by: {} in {} order", sortBy, direction);

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        // Repository 호출 (검색 및 페이징)
        Page<Board> boardPage = boardRepository.getSearchResult(pageable, category, keyword, searchType);

        if (boardPage.isEmpty()) {
            log.warn("No boards found for the given criteria - Page: {}, Size: {}", page, size);
            return Page.empty();
        }

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
        foundBoard.setModifiedAt(LocalDateTime.now());
        boardRepository.save(foundBoard);
        BoardResponseDto modifiedBoard = new BoardResponseDto(foundBoard);
        return modifiedBoard;
    }
}
