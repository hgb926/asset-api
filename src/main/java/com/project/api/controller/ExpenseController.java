package com.project.api.controller;

import com.project.api.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/expense")
@Slf4j
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService service;
}
