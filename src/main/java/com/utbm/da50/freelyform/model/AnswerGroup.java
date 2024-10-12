package com.utbm.da50.freelyform.model;

import com.mongodb.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AnswerGroup {

    @Id
    private String id;

    @NonNull
    private String prefabId;
    private String token;
    private List<AnswerQuestion> questions;
}