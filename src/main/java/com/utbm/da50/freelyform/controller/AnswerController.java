package com.utbm.da50.freelyform.controller;

import com.utbm.da50.freelyform.dto.answer.AnswerRequest;
import com.utbm.da50.freelyform.model.AnswerGroup;
import com.utbm.da50.freelyform.model.User;
import com.utbm.da50.freelyform.service.AnswerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("v1/answers")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping("/{prefab_id}")
    @Operation(summary = "Create an answer to the prefab")
    public void submitAnswer(@AuthenticationPrincipal User user,
                             @PathVariable String prefab_id, @RequestBody AnswerRequest request) {
        answerService.processAnswer(prefab_id, user, request);
    }

    @GetMapping("/{prefab_id}/{answer_id}")
    @Operation(summary = "Get the specified answer")
    public AnswerGroup getAnswer(@PathVariable String prefab_id, @PathVariable String answer_id) {
        return answerService.getAnswerGroup(prefab_id, answer_id);
    }
}
