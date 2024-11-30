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
}
