package com.utbm.da50.freelyform.model.validationRules;

import com.utbm.da50.freelyform.enums.TypeRule;
import com.utbm.da50.freelyform.model.Field;
import org.springframework.stereotype.Component;

@Component
public class MaxLengthRule implements ValidationRule {

    @Override
    public void validate(String userInput, Field field) {
        int maxLength = Integer.parseInt(field.getValidationRules()
                .stream()
                .filter(rule -> rule.getType() == TypeRule.MAX_LENGTH)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Max length rule missing"))
                .getValue());

        if (userInput.length() > maxLength) {
            throw new IllegalArgumentException("Text exceeds maximum length of " + maxLength);
        }
    }
}