package com.utbm.da50.freelyform.service;

import com.mongodb.lang.NonNull;
import com.utbm.da50.freelyform.dto.answer.AnswerRequest;
import com.utbm.da50.freelyform.model.AnswerGroup;
import com.utbm.da50.freelyform.repository.AnswerRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    public void processAnswer(String prefabId, @NonNull AnswerRequest request) {
        String token = request.getToken();
        validateUniqueUserResponse(prefabId, token);

        AnswerGroup answerGroup = new AnswerGroup();
        answerGroup.setPrefabId(prefabId);
        answerGroup.setToken(token);
        answerGroup.setQuestions(request.getQuestions());

        answerRepository.save(answerGroup);
    }

    public AnswerGroup getAnswerGroup(String prefabId, String answerId) {
        return answerRepository.findByPrefabIdAndId(prefabId, answerId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("No response found for prefabId '%s' and answerId '%s'.", prefabId, answerId)
                ));
    }

    public void validateUniqueUserResponse(String prefabId, String token) {
        if(StringUtils.isNotBlank(token) && answerRepository.existsByPrefabIdAndToken(prefabId, token)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    String.format("A response with prefabId '%s' and token '%s' already exists.",
                            prefabId, token)
            );
        }
    }
}