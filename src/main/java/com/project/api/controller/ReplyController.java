package com.project.api.controller;

import com.project.api.dto.request.ReplySaveDto;
import com.project.api.entity.Reply;
import com.project.api.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reply")
@Slf4j
public class ReplyController {
    private final ReplyService replyService;

    @PostMapping
    public ResponseEntity<?> saveReply(@RequestBody ReplySaveDto dto) {
        log.info("ReplySaveDto : {} ", dto.toString());
        Reply newReply = replyService.saveReply(dto);
        log.info("new Reply : {}", newReply);
        return ResponseEntity.ok().body(newReply);
    }

}
