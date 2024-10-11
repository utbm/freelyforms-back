package com.utbm.da50.freelyform.model;

import com.utbm.da50.freelyform.dto.FieldInput;
import com.utbm.da50.freelyform.enums.TypeField;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Data
@AllArgsConstructor
@Document
@Builder
@NoArgsConstructor
public class Field {
    @Id
    private String id;
    @Setter
    private String label;
    @Setter
    private TypeField type;
    @Setter
    private Boolean optional;
    @Setter
    private Boolean hidden;
    @Setter
    private List<Rule> validationRules;
    @Setter
    private Option options;

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
