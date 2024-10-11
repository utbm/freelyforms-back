package com.utbm.da50.freelyform.controller;

import com.utbm.da50.freelyform.dto.answer.AnswerRequest;
import com.utbm.da50.freelyform.model.AnswerGroup;
import com.utbm.da50.freelyform.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("v1/answers")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping("/{prefab_id}")
    public void submitAnswer(@PathVariable String prefab_id, @RequestBody AnswerRequest request) {
        answerService.processAnswer(prefab_id, request);
    }

    @GetMapping("/{prefab_id}/{username}")
    public AnswerGroup getAnswer(@PathVariable String prefab_id, @PathVariable String username) {
        return answerService.getAnswerGroup(prefab_id, username);
    }
}
