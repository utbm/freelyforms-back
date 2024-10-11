package com.utbm.da50.freelyform.service;

import com.utbm.da50.freelyform.exceptions.ValidationFieldException;
import com.utbm.da50.freelyform.model.Prefab;
import com.utbm.da50.freelyform.repository.PrefabRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PrefabService {
    private final PrefabRepository repository;
    private final FieldService fieldService;
    @Autowired
    public PrefabService(PrefabRepository repository, FieldService fieldService) {
        this.repository = repository;
        this.fieldService = fieldService;
    }

    public List<Prefab> getAllPrefabs() {
        return repository.findAll();
    }

    public Prefab createPrefab(Prefab newPrefab) throws ValidationFieldException {
        // check if every field are valid
        newPrefab.getGroups().forEach(group -> fieldService.validateFields(group.getFields()));
        return repository.save(newPrefab);
    }

    public Prefab updatePrefab(String id, Prefab prefab) throws NoSuchElementException, ValidationFieldException {
        Prefab existingPrefab = getPrefabById(id);
        existingPrefab.setName(prefab.getName());
        existingPrefab.setDescription(prefab.getDescription());
        existingPrefab.setTags(prefab.getTags());
        existingPrefab.setGroups(prefab.getGroups());
        // check if every field are valid
        existingPrefab.getGroups().forEach(group -> fieldService.validateFields(group.getFields()));
        return repository.save(existingPrefab);
    }

    public Prefab deletePrefab(String id) throws NoSuchElementException{
        Prefab prefabToDelete = getPrefabById(id);
        repository.deleteById(id);
        return prefabToDelete;
    }

    public Prefab getPrefabById(String id) throws NoSuchElementException {
        Prefab p = repository.findById(id).orElse(null);
        if (p == null) {
            throw new NoSuchElementException("Prefab not found");
        }
        return p;
    }
}
