package com.utbm.da50.freelyform.model;

import com.mongodb.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents a group of answers submitted by a user for a specific prefab.
 */
@Document
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AnswerGroup {

    /**
     * Unique identifier for the answer group.
     */
    @Id
    private String id;

    /**
     * Identifier for the prefab associated with the answers.
     * This field is required and cannot be null.
     */
    @NonNull
    private String prefabId;

    /**
     * Identifier for the user who submitted the answers.
     */
    private String userId;

    /**
     * Date when the answer group was created.
     * This field is required and cannot be null.
     */
    @NonNull
    private LocalDate createdAt;

    /**
     * List of answer subgroups submitted by the user.
     */
    private List<AnswerSubGroup> answers;
}