package com.utbm.da50.freelyform.model;

import com.mongodb.lang.NonNull;
import lombok.Data;

/**
 * Represents an individual question and its corresponding answer within a form.
 */
@Data
public class AnswerQuestion {

    /**
     * The question being answered.
     * This field is required and cannot be null.
     */
    @NonNull
    private String question;

    /**
     * The answer provided for the question.
     * This field can hold any type of answer.
     */
    private Object answer;

    /**
     * Constructs an AnswerQuestion with the specified question and answer.
     *
     * @param question the question being answered, cannot be null.
     * @param answer   the answer provided for the question, can be of any type.
     */
    public AnswerQuestion(@NonNull String question, Object answer) {
        this.question = question;
        this.answer = answer;
    }
}
