package com.utbm.da50.freelyform.controller;

import com.utbm.da50.freelyform.dto.answer.AnswerRequest;
import com.utbm.da50.freelyform.model.AnswerGroup;
import com.utbm.da50.freelyform.model.User;
import com.utbm.da50.freelyform.service.AnswerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling answer-related requests.
 */
@RestController
@RequestMapping("v1/answers")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    /**
     * Submits an answer to a specified prefab.
     *
     * @param user      the authenticated user submitting the answer
     * @param prefab_id the ID of the prefab for which the answer is being submitted
     * @param request   the answer request containing the user's responses
     */
    @PostMapping("/{prefab_id}")
    @Operation(summary = "Create an answer to the prefab")
    public void submitAnswer(@AuthenticationPrincipal User user,
                             @PathVariable String prefab_id, @RequestBody AnswerRequest request) {
        answerService.processAnswer(prefab_id, user, request);
    }

    /**
     * Retrieves the specified answer for a given prefab.
     *
     * @param prefab_id the ID of the prefab
     * @param answer_id the ID of the answer to retrieve
     * @return the answer group associated with the specified prefab and answer IDs
     */
    @GetMapping("/{prefab_id}/{answer_id}")
    @Operation(summary = "Get the specified answer")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("isAuthenticated()")
    public AnswerGroup getAnswer(@PathVariable String prefab_id, @PathVariable String answer_id) {
        return answerService.getAnswerGroup(prefab_id, answer_id);
    }
}
