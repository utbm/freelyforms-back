package com.utbm.da50.freelyform.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.lang.NonNull;
import com.utbm.da50.freelyform.dto.answer.AnswerOutput;
import com.utbm.da50.freelyform.dto.answer.AnswerRequest;
import com.utbm.da50.freelyform.dto.answer.AnswerUser;
import com.utbm.da50.freelyform.enums.TypeField;
import com.utbm.da50.freelyform.enums.TypeRule;
import com.utbm.da50.freelyform.exceptions.UniqueResponseException;
import com.utbm.da50.freelyform.exceptions.ValidationException;
import com.utbm.da50.freelyform.exceptions.ResourceNotFoundException;
import com.utbm.da50.freelyform.model.*;
import com.utbm.da50.freelyform.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Service class for processing and validating answers submitted by users.
 */
@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final PrefabService prefabService;
    private final UserService userService;

    /**
     * Processes a user's answer by validating it and saving it to the repository.
     *
     * @param prefabId the ID of the prefab associated with the answer
     * @param user     the user submitting the answer
     * @param request  the answer request containing the answers
     * @throws UniqueResponseException if a unique response exists or validation fails
     */
    public void processAnswer(String prefabId, User user, @NonNull AnswerRequest request) {
        String userId = Optional.ofNullable(user).map(User::getId).orElse("guest");
        validateUniqueUserResponse(prefabId, userId);
        checkFormPrefab(prefabId, request);

        AnswerGroup answerGroup = new AnswerGroup();
        answerGroup.setPrefabId(prefabId);
        answerGroup.setCreatedAt(LocalDate.now());
        answerGroup.setUserId(userId);
        answerGroup.setAnswers(request.getAnswers());

        answerRepository.save(answerGroup);
    }

    /**
     * Retrieves an answer group by prefab ID and answer ID.
     *
     * @param prefabId the ID of the prefab
     * @param answerId the ID of the answer
     * @return the found AnswerGroup
     * @throws ResourceNotFoundException if no response is found for the provided IDs
     */
    public AnswerOutput getAnswerGroup(String prefabId, String answerId, User user) {
        System.out.println(user);
        String userId = "guest";

        if(user != null)
            userId = user.getId();

        AnswerGroup answerGroup = answerRepository.findByPrefabIdAndIdAndUserId(prefabId, answerId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("No response found for prefabId '%s' and answerId '%s'", prefabId, answerId)
                ));

        return convertAnswerGroup(answerGroup, userId, user);
    }

    /**
     * Retrieves answer groups by prefab ID
     *
     * @param prefabId the ID of the prefab
     * @return the found AnswerGroup
     * @throws ResourceNotFoundException if no response is found for the provided IDs
     */
    public List<AnswerOutput> getAnswerGroupByPrefabId(String prefabId, User user){
        List<AnswerGroup> answerGroup;
        String userId = "guest";

        if(user != null)
            userId = user.getId();

        answerGroup = answerRepository.findByPrefabIdAndUserId(prefabId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("No response found for prefabId '%s'", prefabId)
                ));



        List<AnswerOutput> answerOutputs = new ArrayList<>();
        for (AnswerGroup group : answerGroup) {
            answerOutputs.add(convertAnswerGroup(group, userId, user));
        }

        return answerOutputs;
    }

    public AnswerOutput convertAnswerGroup(AnswerGroup answerGroup, String userId, User user) {
        AnswerUser answer_user = new AnswerUser();
        answer_user.setName("Guest");
        answer_user.setEmail("");

        if(!Objects.equals(userId, "guest")){
            answer_user.setName(String.format("%s %s", user.getFirstName(), user.getLastName()));
            answer_user.setEmail(user.getEmail());
        }

        return new AnswerOutput(
                answerGroup.getId(),
                answerGroup.getPrefabId(),
                answer_user,
                answerGroup.getCreatedAt(),
                answerGroup.getAnswers()
        );
    }

    /**
     * Validates that a user has not already responded to the specified prefab.
     *
     * @param prefabId the ID of the prefab
     * @param userId   the ID of the user
     * @throws UniqueResponseException if a response with the same prefab ID and user ID already exists
     */
    public void validateUniqueUserResponse(String prefabId, String userId) {
        if (!Objects.equals(userId, "guest") && answerRepository.existsByPrefabIdAndUserId(prefabId, userId)) {
            throw new UniqueResponseException(
                    String.format("A response with prefabId '%s' and userId '%s' already exists.", prefabId, userId)
            );
        }
    }

    /**
     * Checks that the prefab is active and the number of answer groups matches the prefab's groups.
     *
     * @param prefabId the ID of the prefab
     * @param request  the answer request containing the answers
     * @throws ValidationException if the prefab is inactive or the number of groups does not match
     */
    public void checkFormPrefab(String prefabId, AnswerRequest request) {
        Prefab prefab = prefabService.getPrefabById(prefabId, false);

        if(!prefab.getIsActive())
            throw new ValidationException("The prefab is inactive.");

        List<Group> prefabGroups = prefab.getGroups();
        List<AnswerSubGroup> answerGroups = request.getAnswers();

        if (prefabGroups.size() != answerGroups.size()) {
            throw new ValidationException("Number of groups in the prefab does not match the number of answer groups.");
        }

        for (int i = 0; i < prefabGroups.size(); i++) {
            checkAnswerGroup(prefabGroups.get(i), answerGroups.get(i), Integer.toString(i));
        }
    }

    /**
     * Checks that the answer group matches the prefab group.
     *
     * @param prefabGroup the prefab group to check against
     * @param answerGroup the answer group to validate
     * @param index      the index of the group in the list
     * @throws ValidationException if the groups do not match
     */
    public void checkAnswerGroup(Group prefabGroup, AnswerSubGroup answerGroup, String index) {
        if (!prefabGroup.getName().equals(answerGroup.getGroup())) {
            throw new ValidationException(
                    String.format("Group index '%s': Prefab and Answer names don't match.\nPrefab: '%s', Answer: '%s'",
                            index, prefabGroup.getName(), answerGroup.getGroup())
            );
        }

        List<Field> fields = prefabGroup.getFields();
        List<AnswerQuestion> questions = answerGroup.getQuestions();

        if (fields.size() != questions.size()) {
            throw new ValidationException(String.format("Group index '%s': Mismatch in number of fields and questions.",
                    index));
        }

        for (int i = 0; i < fields.size(); i++) {
            checkAnswerField(fields.get(i), questions.get(i));
        }
    }

    /**
     * Validates the answer for a specific field.
     *
     * @param field    the field to validate against
     * @param question the answer question to validate
     * @throws ValidationException if validation fails
     */
    public void checkAnswerField(Field field, AnswerQuestion question) {
        validateFieldAndQuestion(field.getLabel(), question.getQuestion());

        TypeField type = field.getType();
        Object answer = question.getAnswer();

        if (answer instanceof LinkedHashMap<?, ?> mapAnswer) {
            if (mapAnswer.isEmpty() && !field.getOptional()) {
                throw new ValidationException(String.format("Answer at the question '%s' is empty.",
                        question.getQuestion()));
            }
            if(mapAnswer.isEmpty())
                return;
        }

        validateAnswerType(answer, type);
        field.getValidationRules().forEach(rule -> checkAnswerRule(answer, rule, field));
    }

    /**
     * Validates that the field matches the question.
     *
     * @param field   the field name
     * @param question the question text
     * @throws ValidationException if the field and question do not match
     */
    private void validateFieldAndQuestion(String field, String question) {
        if (!Objects.equals(field, question)) {
            throw new ValidationException(String.format("Field mismatch: Field '%s' does not match question '%s'.",
                    field, question));
        }
    }

    /**
     * Validates the type of the answer against the expected type.
     *
     * @param answer the answer object to validate
     * @param type   the expected type of the field
     * @throws ValidationException if the answer does not match the expected type and the type is unsupported
     */
    private void validateAnswerType(Object answer, TypeField type) {
        switch (type) {
            case TEXT:
                if (!(answer instanceof String)) {
                    throw new ValidationException(String.format("Answer '%s' is not a string", answer));
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
                    throw new ValidationException(String.format("Answer '%s' is not a list", answer));
                }
                break;
            default:
                throw new ValidationException("Unsupported TypeField: " + type);
        }
    }

    /**
     * Validates a numeric answer.
     *
     * @param answer the answer object to validate
     * @throws ValidationException if the answer is not a valid number
     */
    private void validateNumericAnswer(Object answer) {
        try {
            Float.parseFloat((String) answer);
        } catch (NumberFormatException e) {
            throw new ValidationException(String.format("Answer '%s' is not a valid number", answer));
        }
    }

    /**
     * Validates a date answer.
     *
     * @param answer the answer object to validate
     * @throws ValidationException if the answer is not a valid date
     */
    private void validateDateAnswer(Object answer) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate.parse((String) answer, formatter);
        } catch (DateTimeParseException e) {
            throw new ValidationException(String.format("Answer '%s' is not a valid date", answer));
        }
    }

    /**
     * Validates a geolocation answer.
     *
     * @param answer the answer object to validate
     * @throws ValidationException if the answer is not a valid geolocation
     */
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
                throw new ValidationException("Geolocation answer must contain both 'lat' and 'lng' fields.");
            }

        } catch (Exception e) {
            throw new ValidationException(String.format("Answer '%s' is not a valid geolocation", answer));
        }
    }

    /**
     * Checks the answer rule against the provided answer.
     *
     * @param answer the answer to validate
     * @param rule   the validation rule to apply
     * @param field  the field associated with the rule
     * @throws ValidationException if the rule validation fails
     */
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
                throw new ValidationException("Unsupported TypeRule: " + type);
        }
    }

    /**
     * Checks if the given answer matches the specified regex pattern.
     *
     * @param regex the regex pattern to match against
     * @param answer the answer to validate
     * @param type   the type of the answer (e.g., "email")
     * @throws ValidationException if the answer is not a valid string or
     * if the answer does not match the regex pattern
     */
    private void checkRegex(String regex, Object answer, String type) {
        if (!(answer instanceof String)) {
            throw new ValidationException(String.format("Answer '%s' is not a valid %s", answer, type));
        }

        Matcher matcher = Pattern.compile(type.equals("email") ? "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$" : regex)
                .matcher((String) answer);
        if (!matcher.matches()) {
            throw new ValidationException(String.format("Answer '%s' is not a valid %s", answer, type));
        }
    }

    /**
     * Validates the answer against multiple-choice options.
     *
     * @param answer the answer to validate
     * @param type   the type of rule for validation (e.g., IS_RADIO)
     * @param options the options containing valid choices
     * @throws ValidationException if the answer is not a list or does not meet the validation criteria
     */
    public void checkMultiChoice(Object answer, TypeRule type, Option options) {
        if (!(answer instanceof List)) {
            throw new ValidationException(String.format("Answer '%s' is not a list", answer));
        }

        List<String> answers = (List<String>) answer;
        if (type == TypeRule.IS_RADIO && answers.size() != 1) {
            throw new ValidationException(String.format("Must contain one single answer: '%s'", answer));
        }

        List<String> choices = options.getChoices();
        boolean found = answers.stream().anyMatch(choices::contains);

        if (!found) {
            throw new ValidationException(String.format("Answer '%s' is not an option of the list '%s'",
                    answer, choices));
        }
    }

    /**
     * Checks the interval values for the given answer based on the type rule.
     *
     * @param value the string value to check
     * @param type  the type of rule (e.g., MIN_LENGTH, MAX_LENGTH)
     * @param answer the answer to validate
     */
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

    /**
     * Validates the length of the given answer against specified limits.
     *
     * @param answer the answer to validate
     * @param limit  the length limit for validation
     * @param type   the type of rule for length validation (e.g., MIN_LENGTH, MAX_LENGTH)
     * @throws ValidationException if the answer is not a string or does not meet the length requirement
     */
    private void validateStringLength(Object answer, int limit, TypeRule type) {
        if (!(answer instanceof String answerStr)) {
            throw new ValidationException(String.format("Answer '%s' is not a string", answer));
        }
        if ((type == TypeRule.MIN_LENGTH && answerStr.length() < limit) ||
                (type == TypeRule.MAX_LENGTH && answerStr.length() > limit)) {
            throw new ValidationException(String.format("Answer '%s' does not meet the length requirement for '%s'",
                    answer, type));
        }
    }

    /**
     * Validates the numeric value of the answer against a specified limit.
     *
     * @param answer the answer to validate
     * @param limit  the numeric limit for validation
     * @param type   the type of rule for numeric validation (e.g., MIN_VALUE, MAX_VALUE)
     * @throws ValidationException if the answer does not meet the value requirement
     */
    private void validateNumericValue(Object answer, int limit, TypeRule type) {
        float answerValue = Float.parseFloat((String) answer);
        if ((type == TypeRule.MIN_VALUE && answerValue < limit) ||
                (type == TypeRule.MAX_VALUE && answerValue > limit)) {
            throw new ValidationException(String.format("Answer '%s' does not meet the value requirement for '%s'",
                    answer, type));
        }
    }
}