package com.project.api.controller;

import com.project.api.dto.request.BoardSaveDto;
import com.project.api.entity.Board;
import com.project.api.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        log.info("new Board : ", newBoard);
        return ResponseEntity.ok().body(newBoard);
    }

}
