package fr.utbm.da50.freelyforms.core.controller;


import com.sun.istack.NotNull;
import fr.utbm.da50.freelyforms.core.entity.FormData;
import fr.utbm.da50.freelyforms.core.exception.formdata.FieldNotFoundException;
import fr.utbm.da50.freelyforms.core.exception.formdata.GroupNameNotFoundException;
import fr.utbm.da50.freelyforms.core.exception.formdata.InvalidFormDataException;
import fr.utbm.da50.freelyforms.core.exception.formdata.NoExistingFormDataException;
import fr.utbm.da50.freelyforms.core.exception.prefab.NoExistingPrefabException;
import fr.utbm.da50.freelyforms.core.exception.prefab.rule.RuleException;
import fr.utbm.da50.freelyforms.core.service.FormDataService;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
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
    public List<Object> getAllFormDataFromPrefabField(@NonNull @NotNull @PathVariable("prefab") String prefabName,
                                                      @NonNull @NotNull @PathVariable("group") String groupName,
                                                      @NonNull @NotNull @PathVariable("field") String fieldName) {
        try {
            List<Object> formDataFromPrefabField = service.getAllFormDataFromPrefabField(prefabName, groupName, fieldName);
            if (formDataFromPrefabField.isEmpty())
                throw new ResponseStatusException(HttpStatus.NO_CONTENT);
            return formDataFromPrefabField;
        } catch (NoExistingPrefabException | GroupNameNotFoundException | FieldNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    /**
     * POST /api/formdata/{prefab}
     * @param formData the data to save
     */
    @PostMapping("{prefab}")
    @ResponseBody
    // POST formdata related to a prefab
    public void postFormData(@NonNull @NotNull @RequestBody FormData formData, @NonNull @PathVariable("prefab") String prefabName) {
        try {
            service.postFormData(formData, prefabName);
        } catch (InvalidFormDataException | NoExistingPrefabException | GroupNameNotFoundException | FieldNotFoundException | RuleException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * PATCH/PUT /api/formdata/{prefab}
     * @param formData the modified form data object (identified by its id)
     */
    @RequestMapping(value = "{prefab}", method = {RequestMethod.PATCH, RequestMethod.PUT})
    @ResponseBody
    // PATCH/PUT formdata
    public void patchFormData(@NonNull @NotNull @RequestBody FormData formData) {
        try {
            service.patchFormData(formData);
        } catch (NoExistingFormDataException | InvalidFormDataException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * DELETE /api/formdata
     * @param formDataId the form data object to delete (identified by its id)
     */
    @DeleteMapping
    @ResponseBody
    // DELETE formdata
    public void deleteFormData(@NonNull @NotNull @RequestBody ObjectId formDataId) {
        try {
            service.deleteFormData(formDataId);
        } catch (NoExistingFormDataException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
