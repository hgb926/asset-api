package com.project.api.controller;


import com.project.api.dto.request.IncomeSaveDto;
import com.project.api.entity.Income;
import com.project.api.service.IncomeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/income")
@Slf4j
@RequiredArgsConstructor
public class IncomeController {

    private final IncomeService service;

    @PostMapping("/add-income")
    public ResponseEntity<?> addIncome(@RequestBody IncomeSaveDto dto) {
        log.info("save income dto - {}", dto);
        Income newIncome = service.addIncome(dto);
        return ResponseEntity.ok().body(newIncome);
    }

    @PatchMapping("/{incomeId}")
    public ResponseEntity<?> modifyIncome(@PathVariable Long incomeId, @RequestBody IncomeSaveDto dto) {
        Income income = service.modifyIncome(incomeId, dto);
        return ResponseEntity.ok().body(income);
    }

    @DeleteMapping("/{incomeId}/{userId}")
    public ResponseEntity<?> deleteIncome(@PathVariable Long incomeId, @PathVariable Long userId) {
        service.deleteIncome(incomeId, userId);
        return ResponseEntity.ok().body("Delete income successfully");
    }
}
