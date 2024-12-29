package com.project.api.controller;

import com.project.api.dto.request.GoalSaveDto;
import com.project.api.entity.Goal;
import com.project.api.service.GoalService;
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
@RequestMapping("/goal")
public class GoalController {

    private final GoalService goalService;

    @PostMapping
    public ResponseEntity<?> addGoal(@RequestBody GoalSaveDto dto) {
        Goal goal = goalService.addGoal(dto);
        return ResponseEntity.ok().body(goal);
    }
}
