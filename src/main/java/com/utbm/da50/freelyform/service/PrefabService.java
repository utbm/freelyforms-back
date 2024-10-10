package com.utbm.da50.freelyform.service;

import com.utbm.da50.freelyform.model.Prefab;
import com.utbm.da50.freelyform.repository.PrefabRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PrefabService {
    @Autowired
    private PrefabRepository repository;

    public List<Prefab> getAllPrefabs() {
        return repository.findAll();
    }

    public Prefab createPrefab(Prefab newPrefab) {
        return repository.save(newPrefab);
    }

    public Prefab updatePrefab(String id, Prefab prefab) {
        Prefab existingPrefab = repository.findById(id).orElse(null);
        if (existingPrefab == null) {
            throw new IllegalArgumentException("Prefab not found");
        }
        existingPrefab.setName(prefab.getName());
        existingPrefab.setDescription(prefab.getDescription());
        existingPrefab.setTags(prefab.getTags());
        existingPrefab.setGroups(prefab.getGroups());
        return repository.save(existingPrefab);
    }

    public Prefab deletePrefab(String id) {
        Prefab prefabToDelete = repository.findById(id).orElse(null);
        if (prefabToDelete == null) {
            throw new IllegalArgumentException("Prefab not found");
        }
        repository.deleteById(id);
        return prefabToDelete;
    }

    public Prefab getPrefab(String id) {
        return repository.findById(id).orElse(null);
    }
}
