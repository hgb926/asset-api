package com.project.api.controller;

import com.project.api.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reply")
@Slf4j
public class ReplyController {
    private final ReplyService replyService;


}
