package com.utbm.da50.freelyform.dto.answer;

import com.utbm.da50.freelyform.model.AnswerSubGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a request for submitting answers, containing a list of answer subgroups.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerRequest {
    /**
     * A list of answer subgroups associated with the answers in the request.
     */
    private List<AnswerSubGroup> answers;
}
