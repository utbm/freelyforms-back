package com.utbm.da50.freelyform.repository;

import com.utbm.da50.freelyform.model.AnswerGroup;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AnswerRepository extends MongoRepository<AnswerGroup, String> {
    boolean existsByPrefabIdAndUserName(String prefabId, String userName);

    Optional<AnswerGroup> findByPrefabIdAndId(String prefabId, String answerId);
}
