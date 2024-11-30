package com.project.api.controller;

import com.project.api.dto.request.ExpenseSaveDto;
import com.project.api.entity.Expense;
import com.project.api.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expense")
@Slf4j
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService service;

    @PostMapping("/add-expense")
    public ResponseEntity<?> addExpense(@RequestBody ExpenseSaveDto dto) {
        log.info("save expense dto - {}", dto);
        Expense newExpense = service.addExpense(dto);
        return ResponseEntity.ok().body(newExpense);
    }

    @PatchMapping("/{expenseId}")
    public ResponseEntity<?> modifyExpense(@PathVariable Long expenseId, @RequestBody ExpenseSaveDto dto) {
        Expense modifiedExpense = service.modifyExpense(expenseId, dto);
        return ResponseEntity.ok().body(modifiedExpense);
    }
}
