package com.utbm.da50.freelyform.model;

import com.mongodb.lang.NonNull;
import lombok.Data;

@Data
public class AnswerQuestion {
    @NonNull
    private String question;
    private Object answer;

    public AnswerQuestion(Object answer, String question) {
        this.answer = answer;
        this.question = question;
    }
}