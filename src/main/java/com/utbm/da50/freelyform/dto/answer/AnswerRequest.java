package com.utbm.da50.freelyform.dto.answer;

import com.utbm.da50.freelyform.model.AnswerSubGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerRequest {
    private List<AnswerSubGroup> answers;
}
