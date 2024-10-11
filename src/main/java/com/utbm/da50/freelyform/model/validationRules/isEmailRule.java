package com.utbm.da50.freelyform.model.validationRules;

import com.utbm.da50.freelyform.model.Field;
import org.springframework.stereotype.Component;

@Component
public class isEmailRule implements ValidationRule {
    @Override
    public void validate(String userInput, Field field) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (!userInput.matches(emailRegex)) {
            throw new IllegalArgumentException("Invalid email format.");
        }
    }
}
