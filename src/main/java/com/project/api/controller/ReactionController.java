package com.project.api.controller;

import com.project.api.dto.request.ReactionSaveDto;
import com.project.api.entity.Reaction;
import com.project.api.service.ReactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/reaction")
public class ReactionController {

    private final ReactionService reactionService;


    @PostMapping
    public ResponseEntity<?> saveReaction(@RequestBody ReactionSaveDto dto) {
        log.info("reaction save dto : {}", dto);
        Reaction newReaction = reactionService.saveReaction(dto);
        if (newReaction != null) {
            return ResponseEntity.ok().body(newReaction);
        }
        return ResponseEntity.badRequest().body("이미 있음.");
    }
}
