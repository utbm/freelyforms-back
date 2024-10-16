package com.utbm.da50.freelyform.model.validationRules;

import com.utbm.da50.freelyform.model.Field;

public interface ValidationRule {
    void validate(String userInput, Field field);
}
