package fr.utbm.da50.freelyforms.core.controller;

import fr.utbm.da50.freelyforms.core.entity.Prefab;
import fr.utbm.da50.freelyforms.core.repository.PrefabRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private PrefabRepository repo;


    /**
     * GET /api/prefabs
     * @return all prefabs in the database
     */
    @GetMapping("")
    @ResponseBody
    public List<Prefab> getAllPrefabs() {
        return repo.findAll();
    }

    /**
     * GET /api/prefabs/{prefabName}
     * @param name  name of the prefab
     * @return the prefab of name prefaName
     */
    @GetMapping("{name}")
    @ResponseBody
    public Prefab getPrefab(@PathVariable("name") String name){
        return repo.findPrefabByName(name);
    }


    /**
     * POST /api/prefabs/
     * @param newPrefab a prefab to add, unless a prefab of the same name already exists
     * @return the added prefab
     */
    // POST /prefabs/
    //
    @PostMapping("")
    @ResponseBody
    public Prefab postPrefab(@RequestBody Prefab newPrefab) {
        if (!newPrefab.verifyPrefabValidity()) {
            System.out.println("POST prefab: prefab invalid");
            return null;
        } else if (repo.findPrefabByName(newPrefab.getName()) != null) {
            System.out.println("POST prefab: prefab already exists");
            return null;
        }

        newPrefab = repo.save(newPrefab);
        return newPrefab;
    }

    /**
     * PATCH/PUT /api/prefabs/{prefabName}
     * @param name the name of the prefab to modify
     * @param patchedPrefab the new version of that prefab
     * @return the modified prefab
     */
    @RequestMapping(value = "{name}", method = {RequestMethod.PATCH, RequestMethod.PUT})
    @ResponseBody
    public Prefab patchPrefab(@PathVariable("name") String name, @RequestBody Prefab patchedPrefab) {

        Prefab existing = repo.findPrefabByName(name);
        if (existing == null) {
            System.out.println("PATCH/PUT prefab: No prefab by that name exists");
            return null;
        } else if (!existing.getName().equals(patchedPrefab.getName())) {
            System.out.println("PATCH/PUT prefab: Sent prefab does not match request URL");
            return null;
        } else if (!patchedPrefab.verifyPrefabValidity()) {
            System.out.println("PATCH/PUT prefab: Sent prefab is invalid, and may have duplicate groups or fields or invalid rules");
            return null;
        }

        existing = existing.copyPrefab(patchedPrefab);
        existing = repo.save(existing);
        return existing;
    }

    // DELETE /prefabs/name
    // deletes prefab with name "name"
    // might make this method unavailable, require higher privileges,
    // or prevent the deletion of prefabs with associated form responses

    /**
     * DELETE /api/prefabs/{prefabName}
     * This method should require elevated privileges in the future.
     * @param name the name of the prefab to delete
     * @return true if the prefab has been successfully deleted
     */
    @DeleteMapping("{name}")
    public boolean deletePrefab(@PathVariable("name") String name) {
        Prefab toDelete = repo.findPrefabByName(name);
        if (toDelete != null) {
            repo.delete(toDelete);
        }
        return true;
    }

}
