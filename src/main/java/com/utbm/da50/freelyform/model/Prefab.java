package com.utbm.da50.freelyform.model;

import org.springframework.data.annotation.Id;
import com.utbm.da50.freelyform.dto.PrefabInput;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE) // Make the all-args constructor private
@NoArgsConstructor
@Document("prefabs")
@Builder
public class Prefab {
    @Id
    @Getter
    private String id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String description;
    @Getter
    @Setter
    private String[] tags;
    private List<Group> groups;

    @Builder
    public Prefab(String name, String description, String[] tags, List<Group> groups) {
        this.name = name;
        this.description = description;
        this.tags = tags;
        this.groups = groups;
    }

    public PrefabInput toRest() {
        return new PrefabInput(
                id,
                name,
                description,
                tags,
                groups.stream().map(Group::toRest).collect(Collectors.toList())
        );
    }
}
