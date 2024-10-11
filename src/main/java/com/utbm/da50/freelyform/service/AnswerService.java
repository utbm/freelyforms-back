package com.utbm.da50.freelyform.service;

import com.utbm.da50.freelyform.dto.answer.AnswerRequest;
import com.utbm.da50.freelyform.enums.UserStatus;
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

    public void processAnswer(String prefabId, AnswerRequest request) {
        checkUserData(prefabId, request);

        AnswerGroup answerGroup = new AnswerGroup();
        answerGroup.setPrefabId(prefabId);
        answerGroup.setUserStatus(request.getUserStatus());
        answerGroup.setUserName(request.getUserName());
        answerGroup.setQuestions(request.getQuestions());

        answerRepository.save(answerGroup);
    }

    public void checkUserData(String prefabId, AnswerRequest request) {
        String userName = request.getUserName();
        UserStatus userStatus = request.getUserStatus();

        validateUniqueUserResponse(prefabId, userName);
        validateUserNameForGuest(userStatus, userName);
        validateUserNameForConnected(userStatus, userName);
    }

    private void validateUniqueUserResponse(String prefabId, String userName) {
        if (answerRepository.existsByPrefabIdAndUserName(prefabId, userName)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    String.format("A response with prefabId '%s' and username '%s' already exists.",
                            prefabId, userName)
            );
        }
    }

    private void validateUserNameForGuest(UserStatus userStatus, String userName) {
        if (userStatus == UserStatus.GUEST && StringUtils.isNotEmpty(userName)) {
            throw new ResponseStatusException(
                    HttpStatus.NON_AUTHORITATIVE_INFORMATION,
                    "The field 'userName' must be empty when the user has the status 'GUEST'."
            );
        }
    }

    private void validateUserNameForConnected(UserStatus userStatus, String userName) {
        if (userStatus == UserStatus.CONNECTED && StringUtils.isEmpty(userName)) {
            throw new ResponseStatusException(
                    HttpStatus.NON_AUTHORITATIVE_INFORMATION,
                    "The field 'userName' must be filled in when the user has the status 'CONNECTED'."
            );
        }
    }
}
