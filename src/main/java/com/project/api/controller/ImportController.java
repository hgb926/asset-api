package com.project.api.controller;


import com.project.api.dto.request.ImportSaveDto;
import com.project.api.service.ImportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/import")
@Slf4j
@RequiredArgsConstructor
public class ImportController {

    private final ImportService service;

    @PostMapping("/add-import")
    public ResponseEntity<?> addImport(@RequestBody ImportSaveDto dto) {
        log.info("save import dto - {}", dto);
        service.addImport(dto);
        return ResponseEntity.ok().body("saved success");
    }
}
