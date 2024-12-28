package com.project.api.controller;

import com.project.api.auth.TokenProvider;
import com.project.api.auth.TokenProvider.TokenUserInfo;
import com.project.api.dto.request.BoardSaveDto;
import com.project.api.dto.response.BoardResponseDto;
import com.project.api.entity.Board;
import com.project.api.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping
    public ResponseEntity<?> getAllBoard() {
        List<BoardResponseDto> boards = boardService.getBoards()
                .stream()
                .map(BoardResponseDto::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(boards);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<?> getBoardDetail (@PathVariable Long boardId) {
        BoardResponseDto board = boardService.findOne(boardId);
        return ResponseEntity.ok().body(board);
    }
}
