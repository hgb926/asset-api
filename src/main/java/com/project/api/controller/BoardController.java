package com.project.api.controller;

import com.project.api.auth.TokenProvider;
import com.project.api.auth.TokenProvider.TokenUserInfo;
import com.project.api.dto.request.BoardSaveDto;
import com.project.api.dto.response.BoardResponseDto;
import com.project.api.entity.Board;
import com.project.api.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/board")
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<?> saveBoard(@RequestBody BoardSaveDto dto) {
        log.info("board save dto : {}", dto);
        Board newBoard = boardService.saveBoard(dto);
        if (newBoard != null) {
            return ResponseEntity.ok().body(newBoard);
        }
        return ResponseEntity.badRequest().body("내용이 비어있습니다.");
    }

    /**
     * 게시판 목록 (페이징)
     * @param page 페이지 번호 (0부터 시작)
     * @param size 페이지당 항목 수
     * @return Page<BoardResponseDto>
     */
    @GetMapping
    public ResponseEntity<?> getAllBoard(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<BoardResponseDto> boardPage = boardService.getBoards(page, size);
        return ResponseEntity.ok(boardPage);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<?> getBoardDetail (@PathVariable Long boardId) {
        BoardResponseDto board = boardService.findOne(boardId);
        return ResponseEntity.ok().body(board);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long boardId) {
        log.info("boardId : {}", boardId);
        boardService.deleteBoard(boardId);
        log.info("board deleted : {}", boardId);
        return ResponseEntity.ok().body("삭제 완료");
    }
}
