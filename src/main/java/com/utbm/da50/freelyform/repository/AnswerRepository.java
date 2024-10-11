package com.utbm.da50.freelyform.repository;

import com.utbm.da50.freelyform.model.AnswerGroup;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnswerRepository extends MongoRepository<AnswerGroup, String> {
    boolean existsByPrefabIdAndUserName(String prefabId, String userName);
}
