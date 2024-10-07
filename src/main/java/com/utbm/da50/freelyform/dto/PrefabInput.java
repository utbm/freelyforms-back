package com.utbm.da50.freelyform.dto;

import com.utbm.da50.freelyform.model.Prefab;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrefabInput {
    private String name;
    private String description;
    private String[] tags;
    private List<GroupInput> groups;

    public Prefab toPrefab() {
        return new Prefab(
                name,
                description,
                tags,
                groups.stream().map(GroupInput::toGroup).collect(Collectors.toList())
        );
    }
}
