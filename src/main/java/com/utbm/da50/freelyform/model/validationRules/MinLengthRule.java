package com.utbm.da50.freelyform.model.validationRules;

import com.utbm.da50.freelyform.enums.TypeRule;
import com.utbm.da50.freelyform.model.Field;
import org.springframework.stereotype.Component;

@Component
public class MinLengthRule implements ValidationRule {

    @Override
    public void validate(String userInput, Field field) {
        int minLength = Integer.parseInt(field.getValidationRules()
                .stream()
                .filter(rule -> rule.getType() == TypeRule.MIN_LENGTH)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Min length rule missing"))
                .getValue());

        if (userInput.length() < minLength) {
            throw new IllegalArgumentException("Text is shorter than minimum length of " + minLength);
        }
    }
}
