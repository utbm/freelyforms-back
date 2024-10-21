package com.utbm.da50.freelyform.dto.answer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a user in answerOutput
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerUser {
    private String name;
    private String email;
}