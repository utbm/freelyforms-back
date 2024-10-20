package com.utbm.da50.freelyform.controller;

import com.utbm.da50.freelyform.dto.answer.AnswerRequest;
import com.utbm.da50.freelyform.model.AnswerGroup;
import com.utbm.da50.freelyform.model.User;
import com.utbm.da50.freelyform.service.AnswerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

/**
 * Controller for handling answer-related requests.
 */
@RestController
@RequestMapping("v1/answers")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class AnswerController {

    private final AnswerService answerService;

    /**
     * Submits an answer to a specified prefab.
     *
     * @param user      the authenticated user submitting the answer
     * @param prefab_id the ID of the prefab for which the answer is being submitted
     * @param request   the answer request containing the user's responses
     * @return ResponseEntity with appropriate HTTP status
     */
    @PostMapping("/{prefab_id}")
    @Operation(summary = "Create an answer to the prefab")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Answer created"),
            @ApiResponse(responseCode = "400", description = "Incorrect structure for answer"),
            @ApiResponse(responseCode = "404", description = "Prefab not found")
    })
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> submitAnswer(
            @AuthenticationPrincipal User user,
            @PathVariable String prefab_id,
            @RequestBody AnswerRequest request) {
        try {
            answerService.processAnswer(prefab_id, user, request);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Retrieves the specified answer for a given prefab.
     *
     * @param prefab_id the ID of the prefab
     * @param answer_id the ID of the answer to retrieve
     * @return ResponseEntity with the answer group associated with the specified prefab and answer IDs
     */
    @GetMapping("/{prefab_id}/{answer_id}")
    @Operation(summary = "Get the specified answer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Answer details retrieved"),
            @ApiResponse(responseCode = "404", description = "Answer not found")
    })
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AnswerGroup> getSpecificAnswer(
            @PathVariable String prefab_id,
            @PathVariable String answer_id) {
        try {
            AnswerGroup answer = answerService.getAnswerGroup(prefab_id, answer_id);
            return ResponseEntity.ok(answer);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * Retrieves all answers for a given prefab.
     *
     * @param prefab_id the ID of the prefab
     * @return ResponseEntity with the answer groups associated with the specified prefab
     */
    @GetMapping("/{prefab_id}")
    @Operation(summary = "Get all the answers for the specified prefab")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Answers retrieved"),
            @ApiResponse(responseCode = "404", description = "Prefab not found")
    })
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AnswerGroup> getAnswersByPrefabId(@PathVariable String prefab_id) {
        try {
            AnswerGroup answers = answerService.getAnswerGroupByPrefabId(prefab_id);
            return ResponseEntity.ok(answers);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
