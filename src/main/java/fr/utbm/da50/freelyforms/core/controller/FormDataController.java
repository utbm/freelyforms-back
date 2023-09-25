package fr.utbm.da50.freelyforms.core.controller;


import fr.utbm.da50.freelyforms.core.entity.FormData;
import fr.utbm.da50.freelyforms.core.service.FormDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for the FormData API
 * Answers requests to the /api/formdata/ route
 *
 * @author Pourriture
 */
@RequestMapping("api/formdata")
@RestController
public class FormDataController {
    @Autowired
    private FormDataService service;

    /**
     * GET /api/formdata
     * @return all form data in the database
     */
    @GetMapping("")
    @ResponseBody
    public List<FormData> getAllFormData() {
        return service.getAllFormData();
    }

    /**
     * GET /api/formdata/{prefabName}
     * @param prefabName  name of the prefab
     * @return all form data relating to a prefab of name prefabName
     */
    @GetMapping("{prefab}")
    @ResponseBody
    // GET all data relating to a prefab
    public List<FormData>getAllFormDataFromPrefab(@PathVariable("prefab") String prefabName) {
        return service.getAllFormDataFromPrefab(prefabName);
    }

    /**
     * GET /api/formdata/{prefabName}/{groupName}/{fieldName}
     * @param prefabName name of the prefab
     * @param groupName name of the group belonging to the prefab
     * @param fieldName name of the field belonging to above group
     * @return an array of all field values relating to a single prefab.group.field combination
     */
    @GetMapping("{prefab}/{group}/{field}")
    @ResponseBody
    // GET all data relating to a single field (selector from data value)
    public List<String> getAllFormDataFromPrefabField(@PathVariable("prefab") String prefabName,
                                                      @PathVariable("group") String groupName,
                                                      @PathVariable("field") String fieldName) {
        return service.getAllFormDataFromPrefabField(prefabName, groupName, fieldName);
    }

    /**
     * POST /api/formdata/{prefab}
     * @param formData the data to save
     * @return the added form data
     */
    @PostMapping("{prefab}")
    @ResponseBody
    // POST formdata related to a prefab
    public FormData postFormData(@RequestBody FormData formData) {
        return service.postFormData(formData);
    }

    /**
     * PATCH/PUT /api/formdata/{prefab}
     * @param formData the modified form data object (identified by its id)
     * @return the modified form data object after its save
     */
    @RequestMapping(value = "{prefab}", method = {RequestMethod.PATCH, RequestMethod.PUT})
    @ResponseBody
    // PATCH/PUT formdata
    public FormData patchFormData(@RequestBody FormData formData) {
        return service.patchFormData(formData);
    }

    /**
     * DELETE /api/formdata/{prefab}
     * @param formData the form data object to delete (identified by its id)
     * @return true if the deletion was a success
     */
    @DeleteMapping("{prefab}")
    @ResponseBody
    // DELETE formdata
    public boolean deleteFormData(@RequestBody FormData formData) {
        return service.deleteFormData(formData);
    }
}
