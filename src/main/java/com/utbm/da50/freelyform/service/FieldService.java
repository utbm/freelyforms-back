package com.utbm.da50.freelyform.service;

import com.utbm.da50.freelyform.exceptions.ValidationFieldException;
import com.utbm.da50.freelyform.model.Field;
import com.utbm.da50.freelyform.model.factories.FieldValidatorFactory;
import com.utbm.da50.freelyform.model.validationFields.ValidationField;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FieldService {
    private final FieldValidatorFactory validatorFactory;

    public FieldService(FieldValidatorFactory validatorFactory) {
        this.validatorFactory = validatorFactory;
    }

    public void validateFields(List<Field> fields) throws ValidationFieldException {
        for (Field field : fields) {
            ValidationField validator = validatorFactory.getValidator(field.getType());
            validator.validate(field);
        }
    }
}
