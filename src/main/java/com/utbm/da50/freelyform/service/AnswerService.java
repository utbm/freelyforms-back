package com.utbm.da50.freelyform.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.lang.NonNull;
import com.utbm.da50.freelyform.dto.answer.AnswerRequest;
import com.utbm.da50.freelyform.enums.TypeField;
import com.utbm.da50.freelyform.enums.TypeRule;
import com.utbm.da50.freelyform.model.*;
import com.utbm.da50.freelyform.repository.AnswerRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final PrefabService prefabService;

    public void processAnswer(String prefabId, User user, @NonNull AnswerRequest request) {
        String userId = Optional.ofNullable(user).map(User::getId).orElse("");
        validateUniqueUserResponse(prefabId, userId);
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

        if (prefabGroups.size() != answerGroups.size()) {
            throw new RuntimeException("Number of groups in the prefab does not match the number of answer groups.");
        }

        for (int i = 0; i < prefabGroups.size(); i++) {
            checkAnswerGroup(prefabGroups.get(i), answerGroups.get(i), Integer.toString(i));
        }
    }

    public void checkAnswerGroup(Group prefabGroup, AnswerSubGroup answerGroup, String index) {
        if (!prefabGroup.getName().equals(answerGroup.getGroup())) {
            throw new RuntimeException(
                    String.format("Group index '%s': Prefab and Answer names don't match.\nPrefab: '%s', Answer: '%s'",
                            index, prefabGroup.getName(), answerGroup.getGroup())
            );
        }

        List<Field> fields = prefabGroup.getFields();
        List<AnswerQuestion> questions = answerGroup.getQuestions();

        if (fields.size() != questions.size()) {
            throw new RuntimeException(String.format("Group index '%s': Mismatch in number of fields and questions.",
                    index));
        }

        for (int i = 0; i < fields.size(); i++) {
            checkAnswerField(fields.get(i), questions.get(i));
        }
    }

    public void checkAnswerField(Field field, AnswerQuestion question) {
        validateFieldAndQuestion(field.getLabel(), question.getQuestion());

        TypeField type = field.getType();
        Object answer = question.getAnswer();

        if (answer instanceof LinkedHashMap<?, ?> mapAnswer) {
            if (mapAnswer.isEmpty() && !field.getOptional()) {
                throw new RuntimeException(String.format("Answer at the question '%s' is empty.",
                        question.getQuestion()));
            }
            if(mapAnswer.isEmpty())
                return;
        }

        validateAnswerType(answer, type);
        field.getValidationRules().forEach(rule -> checkAnswerRule(answer, rule, field));
    }

    private void validateFieldAndQuestion(String field, String question) {
        if (!Objects.equals(field, question)) {
            throw new RuntimeException(String.format("Field mismatch: Field '%s' does not match question '%s'.",
                    field, question));
        }
    }

    private void validateAnswerType(Object answer, TypeField type) {
        switch (type) {
            case TEXT:
                if (!(answer instanceof String)) {
                    throw new IllegalArgumentException(String.format("Answer '%s' is not a string", answer));
                }
                break;
            case NUMBER:
                validateNumericAnswer(answer);
                break;
            case DATE:
                validateDateAnswer(answer);
                break;
            case GEOLOCATION:
                validateGeolocationAnswer(answer);
                break;
            case MULTIPLE_CHOICE:
                if (!(answer instanceof List)) {
                    throw new IllegalArgumentException(String.format("Answer '%s' is not a list", answer));
                }
                break;
            default:
                throw new UnsupportedOperationException("Unsupported TypeField: " + type);
        }
    }

    private void validateNumericAnswer(Object answer) {
        try {
            Float.parseFloat((String) answer);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("Answer '%s' is not a valid number", answer));
        }
    }

    private void validateDateAnswer(Object answer) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate.parse((String) answer, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(String.format("Answer '%s' is not a valid date", answer));
        }
    }

    private void validateGeolocationAnswer(Object answer) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode jsonNode;

            if (answer instanceof String) {
                jsonNode = objectMapper.readTree((String) answer);
            } else {
                String jsonString = objectMapper.writeValueAsString(answer);
                jsonNode = objectMapper.readTree(jsonString);
            }

            if (jsonNode.has("lat") && jsonNode.has("lng")) {
                double lat = jsonNode.get("lat").asDouble();
                double lng = jsonNode.get("lng").asDouble();

                System.out.println("Latitude: " + lat);
                System.out.println("Longitude: " + lng);
            } else {
                throw new IllegalArgumentException("Geolocation answer must contain both 'lat' and 'lng' fields.");
            }

        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Answer '%s' is not a valid geolocation", answer), e);
        }
    }

    public void checkAnswerRule(Object answer, Rule rule, Field field) {
        Option options = field.getOptions();
        TypeRule type = rule.getType();
        String value = rule.getValue();

        switch (type) {
            case IS_EMAIL:
                checkRegex(value, answer, "email");
                break;
            case REGEX_MATCH:
                checkRegex(value, answer, "regex");
                break;
            case IS_RADIO:
            case IS_MULTIPLE_CHOICE:
                checkMultiChoice(answer, type, options);
                break;
            case MAX_LENGTH:
            case MIN_LENGTH:
            case MAX_VALUE:
            case MIN_VALUE:
                checkIntervalValues(value, type, answer);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported TypeRule: " + type);
        }
    }

    private void checkRegex(String regex, Object answer, String type) {
        if (!(answer instanceof String)) {
            throw new IllegalArgumentException(String.format("Answer '%s' is not a valid %s", answer, type));
        }

        Matcher matcher = Pattern.compile(type.equals("email") ? "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$" : regex)
                .matcher((String) answer);
        if (!matcher.matches()) {
            throw new RuntimeException(String.format("Answer '%s' is not a valid %s", answer, type));
        }
    }

    public void checkMultiChoice(Object answer, TypeRule type, Option options) {
        if (!(answer instanceof List)) {
            throw new RuntimeException(String.format("Answer '%s' is not a list", answer));
        }

        List<String> answers = (List<String>) answer;
        if (type == TypeRule.IS_RADIO && answers.size() != 1) {
            throw new RuntimeException(String.format("Must contain one single answer: '%s'", answer));
        }

        List<String> choices = options.getChoices();
        boolean found = answers.stream().anyMatch(choices::contains);

        if (!found) {
            throw new RuntimeException(String.format("Answer '%s' is not an option of the list '%s'", answer, choices));
        }
    }

    public void checkIntervalValues(String value, TypeRule type, Object answer) {
        if (value.isEmpty()) {
            return;
        }

        int limit = Integer.parseInt(value);
        if (type == TypeRule.MIN_LENGTH || type == TypeRule.MAX_LENGTH) {
            validateStringLength(answer, limit, type);
        } else {
            validateNumericValue(answer, limit, type);
        }
    }

    private void validateStringLength(Object answer, int limit, TypeRule type) {
        if (!(answer instanceof String answerStr)) {
            throw new RuntimeException(String.format("Answer '%s' is not a string", answer));
        }
        if ((type == TypeRule.MIN_LENGTH && answerStr.length() < limit) ||
                (type == TypeRule.MAX_LENGTH && answerStr.length() > limit)) {
            throw new RuntimeException(String.format("Answer '%s' does not meet the length requirement for '%s'",
                    answer, type));
        }
    }

    private void validateNumericValue(Object answer, int limit, TypeRule type) {
        float answerValue = Float.parseFloat((String) answer);
        if ((type == TypeRule.MIN_VALUE && answerValue < limit) ||
                (type == TypeRule.MAX_VALUE && answerValue > limit)) {
            throw new RuntimeException(String.format("Answer '%s' does not meet the value requirement for '%s'",
                    answer, type));
        }
    }
}
