package com.utbm.da50.freelyform.repository;

import com.utbm.da50.freelyform.model.Prefab;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrefabRepository extends MongoRepository<Prefab, String> {
}
