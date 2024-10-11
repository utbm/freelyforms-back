package com.utbm.da50.freelyform.model;

import com.mongodb.lang.NonNull;
import lombok.Data;

@Data
public class AnswerQuestion {
    @NonNull
    private String question;
    private Object answer;

    public AnswerQuestion(@NonNull String question, Object answer) {
        this.question = question;
        this.answer = answer;
    }
}