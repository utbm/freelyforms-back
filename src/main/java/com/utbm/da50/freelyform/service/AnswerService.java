package com.utbm.da50.freelyform.service;

import com.mongodb.lang.NonNull;
import com.utbm.da50.freelyform.dto.answer.AnswerRequest;
import com.utbm.da50.freelyform.enums.TypeField;
import com.utbm.da50.freelyform.enums.TypeRule;
import com.utbm.da50.freelyform.model.*;
import com.utbm.da50.freelyform.repository.AnswerRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

       TypeField type = f.getType();
       Object answer = q.getAnswer();

       if(answer == null && !f.getOptional()){
           throw new RuntimeException(
                   String.format("Answer at the question is empty '%s'", question)
           );
       }
       if(type == TypeField.TEXT && !(answer instanceof String)){
           throw new IllegalArgumentException(String.format("Answer '%s' is not a string", answer));
       } else if(type == TypeField.NUMBER && !(answer instanceof Integer)){
           throw new IllegalArgumentException(String.format("Answer '%s' is not an integer", answer));
       } else if(type == TypeField.DATE && !(answer instanceof LocalDate)){
           throw new IllegalArgumentException(String.format("Answer '%s' is not a date", answer));
       } else if(type == TypeField.GEOLOCATION && !(answer instanceof Point)) {
           throw new IllegalArgumentException(String.format("Answer '%s' is not a point", answer));
       } else if(type == TypeField.MULTIPLE_CHOICE && !(answer instanceof List)){
           throw new IllegalArgumentException(String.format("Answer '%s' is not a list", answer));
       }

       List<Rule> rules = f.getValidationRules();
       for (Rule rule : rules) checkAnswerRule(answer, rule, f);
    }

    public void checkAnswerRule(Object answer, Rule r, Field f) {
        Option options = f.getOptions();
        TypeRule type = r.getType();
        String value = r.getValue();

        if(type == TypeRule.IS_EMAIL || type == TypeRule.REGEX_MATCH){
            String regex = value;
            if(type == TypeRule.IS_EMAIL)
                regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher((CharSequence) answer);
            if(!matcher.matches()){
                if(type == TypeRule.IS_EMAIL)
                    throw new RuntimeException(String.format("Answer '%s' is not a valid email address", answer));
                else
                    throw new RuntimeException(String.format("Answer '%s' doesn't match with the regex", answer));
            }
        }

        else if(type == TypeRule.IS_RADIO || type == TypeRule.IS_MULTIPLE_CHOICE){
            if(!(answer instanceof List))
                throw new RuntimeException(String.format("Answer '%s' is not a list", answer));

            List<String> list_answer = (List<String>) answer;

            if(type == TypeRule.IS_RADIO && list_answer.size() != 1)
                throw new RuntimeException(String.format("Must contain one single answer: '%s'", answer));

            List<String> list = options.getChoices();

            boolean found = false;
            for (String s : list_answer) {
                for (String choice : list) {
                    if (Objects.equals(choice, s)) {
                        found = true;
                        break;
                    }
                }
            }

            if(!found)
                throw new RuntimeException(
                        String.format("Answer '%s' is not an option of the list '%s'", answer, list)
                );
        }

        else if(type == TypeRule.MAX_LENGTH || type == TypeRule.MIN_LENGTH
            || type == TypeRule.MAX_VALUE || type == TypeRule.MIN_VALUE){
            if(value == null)
                return;

            int l = Integer.parseInt(value);

            if(type == TypeRule.MIN_LENGTH || type == TypeRule.MAX_LENGTH){
                String answer_str;
                if(answer instanceof String)
                    answer_str = (String) answer;
                else
                    throw new RuntimeException(String.format("Answer '%s' is not an string", answer));

                if(type == TypeRule.MIN_LENGTH && answer_str.length() < l)
                    throw new RuntimeException(String.format("Answer '%s' is too short", answer));
                else if(type == TypeRule.MAX_LENGTH && answer_str.length() > l)
                    throw new RuntimeException(String.format("Answer '%s' is too long", answer));
            }
            else{
                int answer_int;
                if(answer instanceof Integer)
                    answer_int = (Integer) answer;
                else
                    throw new RuntimeException(String.format("Answer '%s' is not an integer", answer));

                if(type == TypeRule.MIN_VALUE && answer_int < l)
                    throw new RuntimeException(String.format("Answer '%s' is too short", answer));
                else if(type == TypeRule.MAX_VALUE && answer_int > l)
                    throw new RuntimeException(String.format("Answer '%s' is too long", answer));
            }
        }
    }
}
