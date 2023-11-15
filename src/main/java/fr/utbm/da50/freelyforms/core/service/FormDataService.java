package fr.utbm.da50.freelyforms.core.service;


import fr.utbm.da50.freelyforms.core.entity.FormData;
import fr.utbm.da50.freelyforms.core.entity.Prefab;
import fr.utbm.da50.freelyforms.core.exception.formdata.FieldNotFoundException;
import fr.utbm.da50.freelyforms.core.exception.formdata.GroupNameNotFoundException;
import fr.utbm.da50.freelyforms.core.exception.formdata.InvalidFormDataException;
import fr.utbm.da50.freelyforms.core.exception.formdata.NoExistingFormDataException;
import fr.utbm.da50.freelyforms.core.exception.prefab.NoExistingPrefabException;
import fr.utbm.da50.freelyforms.core.repository.FormDataRepository;
import fr.utbm.da50.freelyforms.core.repository.PrefabRepository;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Service that acts as an interface between the repository and other classes which may which to fetch data from the database.
 * This service fetches User objects.
 * <p>
 * This service is incomplete.
 * </p>
 * @author Pourriture
 */
@Service
public class FormDataService {

    @Autowired
    private FormDataRepository formDataRepository;

    @Autowired
    private PrefabRepository prefabRepository;


    /**
     * @return all form data in the database
     */
    public List<FormData> getAllFormData() {
        return formDataRepository.findAll();
    }


    /**
     * @param prefabName name of the prefab
     * @return all data relating to a prefab
     */
    public List<FormData>getAllFormDataFromPrefab(String prefabName) {

        return formDataRepository.findFormDataByPrefabName(prefabName);
    }


    /**
     * @param prefabName name of the prefab
     * @param groupName name of the group belonging to the prefab
     * @param fieldName name of the field belonging to above group
     * @return an array of all field values relating to a single prefab.group.field combination
     */
    @NonNull
    public List<String> getAllFormDataFromPrefabField(@NonNull String prefabName, @NonNull String groupName, @NonNull String fieldName) throws NoExistingPrefabException, GroupNameNotFoundException, FieldNotFoundException {
        Prefab prefab = prefabRepository.findPrefabByName(prefabName);
        if (prefab == null)
            throw new NoExistingPrefabException("getAllFormData : no pefab for this name : " + prefabName);

        List<FormData> data = getAllFormDataFromPrefab(prefabName);
        ArrayList<String> ret = new ArrayList<>();

        for (FormData f : data) {
            ret.add(f.getGroup(groupName).getField(fieldName).getValue());
        }
        return ret;
    }


    /**
     * Save form data in the database if it is valid
     * @param formData the data to save
     */
    public void postFormData(FormData formData) throws InvalidFormDataException {
        formData.verifyDataValidity();
        formDataRepository.save(formData);
    }


    /**
     *  Modify form data in the database
     * @param formData the modified form data object (identified by its id)
     */
    public void patchFormData(FormData formData) throws NoExistingFormDataException, InvalidFormDataException {
        // TODO: verify form data validity
        // TODO: verify that the form data exists in the database?
        Optional<FormData> existingFormData = formDataRepository.findById(formData.get_id());
        if(existingFormData.isEmpty())
            throw new NoExistingFormDataException("PATCH/PUT formdata: no formdata with this id exists");
        formData.verifyDataValidity();
        formDataRepository.save(formData);
    }


    /**
     * Delete form data from the database
     * @param formDataId the form data id to delete
     */
    public void deleteFormData(@NonNull ObjectId formDataId) throws NoExistingFormDataException {
        // TODO : verify that safe deletion is possible?
        Optional<FormData> existingFormData = formDataRepository.findById(formDataId);
        if(existingFormData.isEmpty())
            throw new NoExistingFormDataException("DELETE formdata: no formdata with this id exists");
        formDataRepository.deleteById(formDataId);

    }

}
