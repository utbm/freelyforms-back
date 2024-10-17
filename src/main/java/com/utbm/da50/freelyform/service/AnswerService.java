package com.utbm.da50.freelyform.service;

import com.mongodb.lang.NonNull;
import com.utbm.da50.freelyform.dto.answer.AnswerRequest;
import com.utbm.da50.freelyform.model.*;
import com.utbm.da50.freelyform.repository.AnswerRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final PrefabService prefabService;

    public void processAnswer(String prefabId, User user, @NonNull AnswerRequest request) {
        String userId = "";
        if(user != null){
            userId = user.getId();
            validateUniqueUserResponse(prefabId, userId);
        }

        checkFormPrefab(prefabId, request);

        AnswerGroup answerGroup = new AnswerGroup();
        answerGroup.setPrefabId(prefabId);
        answerGroup.setCreatedAt(LocalDate.now());
        answerGroup.setUserId(userId);
        answerGroup.setAnswers(request.getAnswers());

        answerRepository.save(answerGroup);
    }

    public AnswerGroup getAnswerGroup(String prefabId, String answerId) {
        return answerRepository.findByPrefabIdAndId(prefabId, answerId)
                .orElseThrow(() -> new RuntimeException(
                        String.format("No response found for prefabId '%s' and answerId '%s'.", prefabId, answerId)
                ));
    }

    public void validateUniqueUserResponse(String prefabId, String userId) {
        if (StringUtils.isNotBlank(userId) && answerRepository.existsByPrefabIdAndUserId(prefabId, userId)) {
            throw new RuntimeException(
                    String.format("A response with prefabId '%s' and userId '%s' already exists.", prefabId, userId)
            );
        }
    }

    public void checkFormPrefab(String prefabId, AnswerRequest request) {
        Prefab prefab = prefabService.getPrefabById(prefabId, false);
        List<Group> prefabGroups = prefab.getGroups();
        List<AnswerSubGroup> answerGroups = request.getAnswers();

        int prefabGroupSize = prefabGroups.size();
        if (prefabGroupSize != answerGroups.size())
            throw new RuntimeException("Number of groups in the prefab does not match the number of answer groups.");

        for (int i = 0; i < prefabGroupSize; i++)
            checkAnswerGroup(prefabGroups.get(i), answerGroups.get(i), Integer.toString(i));
    }

    public void checkAnswerGroup(Group g, AnswerSubGroup a, String index) {
        String prefabGroupName = g.getName();
        String answerGroupName = a.getGroup();

        if (!prefabGroupName.equals(answerGroupName)){
            throw new RuntimeException(
                    String.format("Group index '%s': Prefab and Answer names don't match." +
                            "\nPrefab: '%s', Answer: '%s'", index, prefabGroupName, answerGroupName)
            );
        }

        List<Field> fields = g.getFields();
        List<AnswerQuestion> questions = a.getQuestions();

        if (fields.size() != questions.size()) {
            throw new RuntimeException(
                    String.format("Group index '%s': Mismatch in number of fields and questions.", index)
            );
        }

        for (int i = 0; i < fields.size(); i++)
            checkAnswerField(fields.get(i), questions.get(i));
    }

    public void checkAnswerField(Field f, AnswerQuestion q) {
       String field = f.getLabel();
       String question = q.getQuestion();
       if(!Objects.equals(field, question)){
           throw new RuntimeException(
                   String.format("Field index '%s': Mismatch in question '%s'.", field, question)
           );
       }
    }
}
