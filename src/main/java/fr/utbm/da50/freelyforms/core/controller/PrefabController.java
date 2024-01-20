package fr.utbm.da50.freelyforms.core.controller;

import com.sun.istack.NotNull;
import fr.utbm.da50.freelyforms.core.entity.Prefab;
import fr.utbm.da50.freelyforms.core.exception.prefab.ExistingPrefabException;
import fr.utbm.da50.freelyforms.core.exception.prefab.InvalidPrefabException;
import fr.utbm.da50.freelyforms.core.exception.prefab.NoExistingPrefabException;
import fr.utbm.da50.freelyforms.core.exception.prefab.PrefabNameNotMatchingException;
import fr.utbm.da50.freelyforms.core.service.PrefabService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Controller for the Prefab API
 * Answers requests to the /api/prefabs/ route
 *
 * @author Pourriture
 */
@RequestMapping("api/prefabs")
@RestController
public class PrefabController {


    @Autowired
    private PrefabService service;


    /**
     * GET /api/prefabs
     * @return all prefabs in the database
     */
    @GetMapping("")
    @ResponseBody
    public List<Prefab> getAllPrefabs() {
        List<Prefab> prefabs = service.getAllPrefabs();
        if (prefabs.isEmpty())
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        return prefabs;
    }

    /**
     * GET /api/prefabs/{prefabName}
     * @param name  name of the prefab
     * @return the prefab of name prefaName
     */
    @GetMapping("{name}")
    @ResponseBody
    public Prefab getPrefab(@NonNull @NotNull @PathVariable("name") String name){
        Prefab prefab = service.getPrefab(name);
        if (Prefab.builder().build().equals(prefab)){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        return prefab;
    }


    /**
     * POST /api/prefabs/
     * @param newPrefab a prefab to add, unless a prefab of the same name already exists
     */
    // POST /prefabs/
    //
    @PostMapping("")
    @ResponseBody
    public void postPrefab(@NotNull @NonNull @RequestBody Prefab newPrefab) {
        try {
            service.postPrefab(newPrefab);
        } catch (InvalidPrefabException | ExistingPrefabException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }

    /**
     * PATCH/PUT /api/prefabs/{prefabName}
     * @param name the name of the prefab to modify
     * @param patchedPrefab the new version of that prefab
     */
    @RequestMapping(value = "{name}", method = {RequestMethod.PATCH, RequestMethod.PUT})
    @ResponseBody
    public void patchPrefab(@NonNull @NotNull @PathVariable("name") String name, @NonNull @NotNull @RequestBody Prefab patchedPrefab) {
        try {
            service.patchPrefab(name, patchedPrefab);
        } catch (NoExistingPrefabException | PrefabNameNotMatchingException |InvalidPrefabException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // DELETE /prefabs/name
    // deletes prefab with name "name"
    // might make this method unavailable, require higher privileges,
    // or prevent the deletion of prefabs with associated form responses

    /**
     * DELETE /api/prefabs/{prefabName}
     * This method should require elevated privileges in the future.
     * @param name the name of the prefab to delete
     */
    @DeleteMapping("{name}")
    public void deletePrefab(@NonNull @NotNull @PathVariable("name") String name) {
        try {
            service.deletePrefab(name);
        } catch (NoExistingPrefabException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
