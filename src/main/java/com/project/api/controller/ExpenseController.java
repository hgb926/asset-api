package com.project.api.controller;

import com.project.api.dto.request.ExpenseSaveDto;
import com.project.api.dto.request.ImportSaveDto;
import com.project.api.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/expense")
@Slf4j
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService service;

    @PostMapping("/add-expense")
    public ResponseEntity<?> addImport(@RequestBody ExpenseSaveDto dto) {
        log.info("save import dto - {}", dto);
        service.addExpense(dto);
        return ResponseEntity.ok().body("saved success");
    }
}
