package fr.utbm.da50.freelyforms.core.service;

import fr.utbm.da50.freelyforms.core.entity.Prefab;
import fr.utbm.da50.freelyforms.core.exception.prefab.ExistingPrefabException;
import fr.utbm.da50.freelyforms.core.exception.prefab.InvalidPrefabException;
import fr.utbm.da50.freelyforms.core.exception.prefab.NoExistingPrefabException;
import fr.utbm.da50.freelyforms.core.exception.prefab.PrefabNameNotMatchingException;
import fr.utbm.da50.freelyforms.core.repository.PrefabRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrefabService {

    @Autowired
    private PrefabRepository repository;

    @NonNull
    public List<Prefab> getAllPrefabs() {
        return repository.findAll();
    }

    @NonNull
    public Prefab getPrefab(String name){
        Prefab prefab = repository.findPrefabByName(name);
        if (prefab == null){
            return Prefab.builder().build();
        }
        return prefab;
    }

    public void postPrefab( Prefab newPrefab) throws InvalidPrefabException, ExistingPrefabException {
        newPrefab.verifyPrefabValidity();
        verifyExistingPrefab(newPrefab);
        repository.save(newPrefab);
    }

    private void verifyExistingPrefab(@NonNull Prefab newPrefab) throws ExistingPrefabException{
        if (repository.findPrefabByName(newPrefab.getName()) != null) {
            System.out.println("POST prefab: prefab already exists");
            throw new ExistingPrefabException("POST prefab: prefab already exists");
        }
    }

    public void patchPrefab(@NonNull String name, @NonNull Prefab patchedPrefab) throws NoExistingPrefabException, PrefabNameNotMatchingException, InvalidPrefabException {
        Prefab existing = repository.findPrefabByName(name);
        if (existing == null) {
            System.out.println("PATCH/PUT prefab: No prefab by that name exists");
            throw new NoExistingPrefabException("PATCH/PUT prefab: No prefab by that name exists");
        }
        if (!existing.getName().equals(patchedPrefab.getName())) {
            System.out.println("PATCH/PUT prefab: Sent prefab does not match request URL");
            throw new PrefabNameNotMatchingException("PATCH/PUT prefab: Sent prefab does not match request URL");
        }
        patchedPrefab.verifyPrefabValidity();

        existing = existing.copyPrefab(patchedPrefab);
        repository.save(existing);
    }

    public void deletePrefab(@NonNull String name) throws NoExistingPrefabException {
        Prefab toDelete = repository.findPrefabByName(name);
        if(toDelete == null)
            throw new NoExistingPrefabException("DELETE prefab : No prefab by that name exists");
        repository.delete(toDelete);
    }
}
