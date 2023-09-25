package fr.utbm.da50.freelyforms.core.service;


import fr.utbm.da50.freelyforms.core.entity.FormData;
import fr.utbm.da50.freelyforms.core.repository.FormDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;


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
    public List<String> getAllFormDataFromPrefabField(String prefabName, String groupName, String fieldName) {
        List<FormData> data = getAllFormDataFromPrefab(prefabName);
        ArrayList<String> ret = new ArrayList<>();

        for (FormData f : data) {
            try {
                ret.add(f.getGroup(groupName).getField(fieldName).getValue());
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return ret;
    }


    /**
     * Save form data in the database if it is valid
     * @param formData the data to save
     * @return the added form data
     */
    public FormData postFormData(FormData formData) {
        // TODO : verify form data validity
        formData = formDataRepository.save(formData);
        return formData;
    }


    /**
     *  Modify form data in the database
     * @param formData the modified form data object (identified by its id)
     * @return the modified form data object after its save
     */
    public FormData patchFormData(FormData formData) {
        // TODO: verify form data validity
        // TODO: verify that the form data exists in the database?
        formData = formDataRepository.save(formData);
        return formData;
    }


    /**
     * Delete form data from the database
     * @param formData the form data object to delete (identified by its id)
     * @return true if the deletion was a success
     */
    public boolean deleteFormData(FormData formData) {
        // TODO : verify that safe deletion is possible?
        // note: should be modified to accept only the ID instead of the entire form data object
        // note 2 : or make an alternative method taking an ObjectId paramter instead of a FormData one

        try {
            formDataRepository.delete(formData);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
