package com.utbm.da50.freelyform.model;

import com.utbm.da50.freelyform.dto.FieldInput;
import com.utbm.da50.freelyform.enums.TypeField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@Document
@Builder
@NoArgsConstructor
public class Field {
    private String id;
    private String label;
    private TypeField type;
    private Boolean optional;
    private Boolean hidden;
    private List<Rule> validationRules;
    private Object options;

    public FieldInput toRest() {
        return new FieldInput(
                id,
                label,
                type,
                optional,
                hidden,
                validationRules.stream().map(Rule::toRest).collect(Collectors.toList()),
                options
        );
    }
}
