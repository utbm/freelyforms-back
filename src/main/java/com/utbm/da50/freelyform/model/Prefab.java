package com.utbm.da50.freelyform.model;

import com.utbm.da50.freelyform.dto.PrefabInput;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@Document("prefabs")
@Builder
@NoArgsConstructor
public class Prefab {
    private String name;
    private String description;
    private String[] tags;
    private List<Group> groups;

    public PrefabInput toRest() {
        return new PrefabInput(
                name,
                description,
                tags,
                groups.stream().map(Group::toRest).collect(Collectors.toList())
        );
    }
}
