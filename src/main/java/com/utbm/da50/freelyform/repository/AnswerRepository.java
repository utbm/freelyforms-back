package com.utbm.da50.freelyform.repository;

import com.utbm.da50.freelyform.model.AnswerGroup;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends MongoRepository<AnswerGroup, String> {
    boolean existsByPrefabIdAndUserId(String prefabId, String token);

    Optional<AnswerGroup> findByPrefabIdAndIdAndUserId(String prefabId, String answerId, String userId);

    Optional<List<AnswerGroup>> findByPrefabIdAndUserId(String prefabId, String userId);
}