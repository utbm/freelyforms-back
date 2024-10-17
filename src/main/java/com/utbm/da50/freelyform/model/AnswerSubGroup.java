package com.utbm.da50.freelyform.model;

import java.util.List;
import com.mongodb.lang.NonNull;
import lombok.Data;

@Data
public class AnswerSubGroup {
    @NonNull
    private String group;
    private List<AnswerQuestion> questions;

    public AnswerSubGroup(@NonNull String group, @NonNull List<AnswerQuestion> questions) {
        this.group = group;
        this.questions = questions;
    }
}
