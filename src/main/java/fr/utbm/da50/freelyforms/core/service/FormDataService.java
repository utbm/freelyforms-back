package fr.utbm.da50.freelyforms.core.service;


import com.mongodb.client.MongoClients;
import fr.utbm.da50.freelyforms.core.entity.FormData;
import fr.utbm.da50.freelyforms.core.entity.formdata.Group;
import fr.utbm.da50.freelyforms.core.entity.formdata.Map;
import fr.utbm.da50.freelyforms.core.entity.formdata.Material;
import fr.utbm.da50.freelyforms.core.repository.FormDataRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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

    @Autowired
    private final MongoTemplate mongoTemplate = new MongoTemplate(MongoClients.create("mongodb+srv://<manman>:<lKlx7IraHVvKg5w1>@cluster0.eplugoj.mongodb.net/?retryWrites=true&w=majority&appName=AtlasApp"),"freelyforms");

    /**
     * Get a formdata from the database
     * @param formDataID the form data object (identified by its id)
     * @return FormData object
     */
    public FormData getFormData(ObjectId formDataID){
        Query query = new Query(Criteria.where("_id").is(formDataID));
        return  mongoTemplate.findOne(query, FormData.class);

    }
    /**
     * Create a new formdata and save it in the database
     * @param formData Formdata Object to be saved
     * @return FormData object
     */
    public FormData postFormData(FormData formData){return mongoTemplate.insert(formData);}
    /**
     * Modify a formdata and save it in the database
     * @param formData Formdata Object to be saved
     * @return FormData object
     */
    public FormData modifyFormData(FormData formData){
        Query query = new Query(Criteria.where("_id").is(formData.get_id()));
        Update update = new Update().set("prefabName",formData.getPrefabName()).set("groups",formData.getGroups());
        mongoTemplate.updateFirst(query,update,FormData.class);
        return formData;
    }
    /**
     * Delete a formdata from the database
     * @param formDataID the form data object to delete (identified by its id)
     * @return true if the deletion was a success
     */
    public boolean deleteFormData(ObjectId formDataID){
        Query query = new Query(Criteria.where("_id").is(formDataID));
        FormData tempFormData = mongoTemplate.findOne(query,FormData.class);
        if(tempFormData != null){
            return mongoTemplate.remove(tempFormData).getDeletedCount() != 0;
        }else {
            return false;
        }
    }
    /**
     * Get all groups of a specific formdata in the database
     * @param formDataID the form data object (identified by its id)
     * @return ArrayList<Group> group list of the formdata
     */
    public ArrayList<Group> getFormDataGroups(ObjectId formDataID){
        Query query = new Query(Criteria.where("_id").is(formDataID));
        FormData tempFormData = mongoTemplate.findOne(query,FormData.class);
        if(tempFormData != null ) return tempFormData.getGroups();
        return null;
    }
    /**
     * Get a specific Group of a formdata from the database
     * @param formDataID the form data object (identified by its id)
     * @return Group
     */
    public Group getFormDataSpecificGroup(ObjectId formDataID, String groupName){
        Query query = new Query(Criteria.where("_id").is(formDataID).and("groups.name").is(groupName));
        FormData tempFormData = mongoTemplate.findOne(query,FormData.class);
        //Criteria.where("groups").elemMatch(Criteria.where("name").is(groupName))
        //Criteria.where("groups.name").is("identitygroup")
        //They both work just care for findOne(,.class);
        if(tempFormData != null ) return tempFormData.getGroup(groupName);
        return null;
    }
    /**
     * Add a Group to a formdata
     * @param formDataID the form data object (identified by its id)
     * @param group the group to be added to the formdata
     * @return ArrayList<Group> group list of the formdata
     */
    public ArrayList<Group> addFormDataSpecificGroup(ObjectId formDataID, Group group){
        Query query = new Query(Criteria.where("_id").is(formDataID));
        FormData tempFormData = mongoTemplate.findOne(query,FormData.class);
        if(tempFormData.addGroup(group)){
            Update update = new Update().set("groups", tempFormData.getGroups());
            mongoTemplate.updateFirst(query,update, FormData.class);
            return tempFormData.getGroups();
        }
        return null;
    }
    /**
     * Modify a specific Group in a formdata
     * @param formDataID the form data object (identified by its id)
     * @param oldGroupName name of the group to be modified in the formdata
     * @param newGroup new group
     * @return ArrayList<Group> group list of the formdata
     */
    public ArrayList<Group> modifyFormDataSpecificGroup(ObjectId formDataID, String oldGroupName, Group newGroup){
        Query query = new Query(Criteria.where("_id").is(formDataID).and("groups.name").is(oldGroupName));
        FormData tempFormData = mongoTemplate.findOne(query,FormData.class);
        if(tempFormData != null){
            Update update = new Update().set("groups.$.fields",newGroup.getFields());
            mongoTemplate.updateFirst(query,update, FormData.class);
            query = new Query(Criteria.where("_id").is(formDataID).and("groups.name").is(oldGroupName));
            tempFormData = mongoTemplate.findOne(query,FormData.class);
            return tempFormData.getGroups();
        }
        return null;
    }
    /**
     * Delete a specific Group from a formdata
     * @param formDataID the form data object (identified by its id)
     * @param groupName name of the group to be deleted
     * @return ArrayList<Group> group list of the formdata
     */
    public ArrayList<Group> deleteFormDataSpecificGroup(ObjectId formDataID, String groupName){
        Query query = new Query(Criteria.where("_id").is(formDataID));
        FormData tempFormData = mongoTemplate.findOne(query,FormData.class);
        if(tempFormData.getGroups().removeIf(p -> p.getName().equals(groupName))){
            Update update = new Update().set("groups", tempFormData.getGroups());
            mongoTemplate.updateFirst(query,update, FormData.class);
            return  tempFormData.getGroups();
        }
        return null;
    }
    /**
     * Get Map of a specific group from a formdata in the database
     * @param formDataID the formdata object (identified by its id)
     * @param groupName name of the group
     * @return Map
     */
    public Map getFormDataMap(ObjectId formDataID, String groupName){
        Query query = new Query(Criteria.where("_id").is(formDataID).and("groups.name").is(groupName));
        FormData tempFormData = mongoTemplate.findOne(query,FormData.class);
        if(tempFormData != null){
            return tempFormData.getGroup(groupName).getMap();
        }
        return null;
    }
    /**
     * Add a Map to a specific group of a formdata in the database
     * @param formDataID the form data object (identified by its id)
     * @param groupName name of the group
     * @return Map added map to a group of the formdata
     */
    public Map postFormDataMap(ObjectId formDataID, String groupName , Map map){
        Query query = new Query(Criteria.where("_id").is(formDataID).and("groups.name").is(groupName));
        FormData tempFormData = mongoTemplate.findOne(query,FormData.class);
        if(tempFormData != null){
            Group tempGroup = tempFormData.getGroup(groupName);
            if (tempGroup != null){
                Update update = new Update().set("groups.$.map",map);
                mongoTemplate.updateFirst(query,update,FormData.class);
                return map;
            }
        }
        return null;
    }
    /**
     * Modify a Map of a specific group of a formdata in the database
     * @param formDataID the form data object (identified by its id)
     * @param groupName name of the group
     * @param map map object to replace the old map
     * @return Map new added map object
     */
    public Map patchFormDataMap(ObjectId formDataID, String groupName , Map map){
        Query query = new Query(Criteria.where("_id").is(formDataID).and("groups.name").is(groupName));
        FormData tempFormData = mongoTemplate.findOne(query,FormData.class);
        if(tempFormData != null){
            Group tempGroup = tempFormData.getGroup(groupName);
            if (tempGroup != null){
                Update update = new Update().set("groups.$.map",map);
                mongoTemplate.updateFirst(query,update,FormData.class);
                return map;
            }
        }
        return null;
    }
    /**
     * Delete a Map of a specific group of a formdata in the database
     * @param formDataID the form data object (identified by its id)
     * @param groupName name of the group
     * @return true if the deletion was a success
     */
    public boolean deleteFormDataMap(ObjectId formDataID, String groupName){
        Query query = new Query(Criteria.where("_id").is(formDataID).and("groups.name").is(groupName));
        FormData tempFormData = mongoTemplate.findOne(query,FormData.class);
        if(tempFormData != null){
            if(tempFormData.getGroup(groupName).getMap() != null){
                Update update = new Update().set("groups.$.map",null);
                mongoTemplate.updateFirst(query,update,FormData.class);
                return true;
            }
        }
        return false;
    }

    /**
     * Get ALL materials of the map in a specific group of a formdata in the database
     * @param formDataID the form data object (identified by its id)
     * @param groupName name of the group
     * @return ArrayList<Material>
     */
    public ArrayList<Material> getFormDataMapMaterialArrayList(ObjectId formDataID, String groupName){
        Query  query = new Query(Criteria.where("_id").is(formDataID)
                                    .and("groups.name").is(groupName));
        FormData tempFormData = mongoTemplate.findOne(query,FormData.class);
        if(tempFormData != null ){
            return tempFormData.getGroup(groupName).getMap().getMaterialArrayList();
        }
        return null;
    }
    /**
     * Get a specific material of the map in a specific group of a formdata in the database
     * @param formDataID the form data object (identified by its id)
     * @param groupName name of the group
     * @param materialName name of the material
     * @return Material
     */
    public Material getFormDataMapMaterial(ObjectId formDataID, String groupName,String materialName){
        Query query = new Query(Criteria.where("_id").is(formDataID)
                .and("groups").elemMatch(Criteria.where("name").is(groupName)
                        .and("map.materialArrayList.name").is(materialName)));
        FormData tempFormData = mongoTemplate.findOne(query,FormData.class);
        if(tempFormData != null ){
            return tempFormData.getGroup(groupName).getMap().getMaterial(materialName);
        }
        return null;
    }
    /**
     * Add a material to list of materials of the map in a specific group of a formdata in the database
     * @param formDataID the form data object (identified by its id)
     * @param groupName name of the group
     * @param material material object
     * @return Material
     */
    public ArrayList<Material> addFormDataMapMaterial(ObjectId formDataID, String groupName, Material material){
        Query  query = new Query(Criteria.where("_id").is(formDataID)
                .and("groups.name").is(groupName));
        FormData tempFormData = mongoTemplate.findOne(query,FormData.class);
        if(tempFormData != null ){
            tempFormData.getGroup(groupName).getMap().addMaterial(material);
            Update update = new Update().set("groups.$.map.materialArrayList",tempFormData.getGroup(groupName).getMap().getMaterialArrayList());
            mongoTemplate.updateFirst(query,update,FormData.class);
            return tempFormData.getGroup(groupName).getMap().getMaterialArrayList();
        }
        return null;
    }
    /**
     * Delete a material of list of materials of a map in a specific group of a formdata in the database
     * @param formDataID the form data object (identified by its id)
     * @param groupName name of the group
     * @return true if the deletion was a success
     */
    public ArrayList<Material> removeFormDataMapMaterial(ObjectId formDataID, String groupName, String materialName){
        Query  query = new Query(Criteria.where("_id").is(formDataID)
                .and("groups.name").is(groupName));
        FormData tempFormData = mongoTemplate.findOne(query,FormData.class);
        if(tempFormData != null ){
            tempFormData.getGroup(groupName).getMap().getMaterialArrayList().removeIf(m->m.getName().equals(materialName));
            Update update = new Update().set("groups.$.map.materialArrayList",tempFormData.getGroup(groupName).getMap().getMaterialArrayList());
            mongoTemplate.updateFirst(query,update,FormData.class);
            return tempFormData.getGroup(groupName).getMap().getMaterialArrayList();
        }
        return null;
    }



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
