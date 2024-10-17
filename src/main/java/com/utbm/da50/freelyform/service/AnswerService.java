package com.utbm.da50.freelyform.service;

import com.mongodb.lang.NonNull;
import com.utbm.da50.freelyform.dto.answer.AnswerRequest;
import com.utbm.da50.freelyform.model.AnswerGroup;
import com.utbm.da50.freelyform.model.User;
import com.utbm.da50.freelyform.repository.AnswerRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;


@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    public void processAnswer(String prefabId, User user, @NonNull AnswerRequest request) {
        String userId = "";
        if(user != null){
            userId = user.getId();
            validateUniqueUserResponse(prefabId, userId);
        }

        AnswerGroup answerGroup = new AnswerGroup();
        answerGroup.setPrefabId(prefabId);
        answerGroup.setCreatedAt(LocalDate.now());
        answerGroup.setUserId(userId);
        answerGroup.setAnswers(request.getAnswers());

        answerRepository.save(answerGroup);
    }

    public AnswerGroup getAnswerGroup(String prefabId, String answerId) {
        return answerRepository.findByPrefabIdAndId(prefabId, answerId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("No response found for prefabId '%s' and answerId '%s'.", prefabId, answerId)
                ));
    }

    public void validateUniqueUserResponse(String prefabId, String userId) {
        if(StringUtils.isNotBlank(userId) && answerRepository.existsByPrefabIdAndUserId(prefabId, userId)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format("A response with prefabId '%s' and userId '%s' already exists.",
                            prefabId, userId)
            );
        }
    }
}
