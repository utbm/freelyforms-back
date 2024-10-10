package com.utbm.da50.freelyform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrefabOutput extends PrefabInput{
    private String id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PrefabOutput(String id, String name, String description,
                        LocalDateTime createdAt, LocalDateTime updatedAt,
                        String[] tags, List<GroupInput> groups) {
        super(name, description, tags, groups);
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
