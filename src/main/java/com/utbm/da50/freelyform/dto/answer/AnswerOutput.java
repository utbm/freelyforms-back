package com.utbm.da50.freelyform.dto.answer;

import com.utbm.da50.freelyform.model.AnswerSubGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerOutput {
    private String id;
    private String prefabId;
    private AnswerUser user;
    private LocalDate createdAt;
    private List<AnswerSubGroup> answers;
}