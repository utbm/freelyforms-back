package com.utbm.da50.freelyform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrefabOutput extends PrefabInput{
    String id;
    public PrefabOutput(String id, String name, String description, String[] tags, List<GroupInput> groups) {
        super(name, description, tags, groups);
        this.id = id;
    }
}
