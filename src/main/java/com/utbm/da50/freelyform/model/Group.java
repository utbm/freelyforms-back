package com.utbm.da50.freelyform.model;

import com.utbm.da50.freelyform.dto.GroupInput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Group {
    private String id;
    private String name;
    private List<Field> fields;

    public GroupInput toRest() {
        return new GroupInput(
                id, name, fields.stream().map(Field::toRest).collect(Collectors.toList())
        );
    }
}
