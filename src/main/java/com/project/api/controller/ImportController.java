package com.project.api.controller;


import com.project.api.service.ImportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/import")
@Slf4j
@RequiredArgsConstructor
public class ImportController {

    private final ImportService service;
}
