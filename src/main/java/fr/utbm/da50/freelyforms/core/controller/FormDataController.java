package fr.utbm.da50.freelyforms.core.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.utbm.da50.freelyforms.core.entity.FormData;
import fr.utbm.da50.freelyforms.core.entity.formdata.Group;
import fr.utbm.da50.freelyforms.core.entity.formdata.Map;
import fr.utbm.da50.freelyforms.core.entity.formdata.Material;
import fr.utbm.da50.freelyforms.core.service.FormDataService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * GET /api/formdata
     * @param formDataID the formdata object (identified by its id)
     * @return FormData Object
     */
    @GetMapping("")
    @ResponseBody
    public FormData getFormData(@RequestParam("formDataID") ObjectId formDataID){
        FormData formData = service.getFormData(formDataID);
        return formData;
    }
    /**
     * POST /api/formdata/post
     * @param formData the formdata object
     * @return FormData Object
     */
    @PostMapping("/post")
    @ResponseBody
    public FormData createFormData(@RequestBody FormData formData){
        if(service.getFormData(formData.get_id()) == null){
            service.postFormData(formData);
            return formData;
        }
        return null;
    }
    /**
     * PATCH(modify) /api/formdata/patch
     * @param formData the formdata object
     * @return FormData Object
     */
    @PatchMapping("/patch")
    @ResponseBody
    public FormData modifyFormData(@RequestBody FormData formData){
        return service.modifyFormData(formData);
    }
    /**
     * DELETE /api/formdata/delete
     * @param requestBodyData contains id of formdata object
     * @return true if deletion was success
     */
    @DeleteMapping("/delete")
    @ResponseBody
    public boolean deleteFormData(@RequestBody JsonNode requestBodyData){
        return service.deleteFormData(objectMapper.convertValue(requestBodyData.get("formDataID"),ObjectId.class));
    }
    /**
     * GET /api/formdata/Group/All}
     * @param formDataID the formdata object (identified by its id)
     * @return ArrayList<Group> group list of formdata
     */
    @GetMapping("/Group/All")
    @ResponseBody
    public ArrayList<Group> getGroupAll(@RequestParam("formDataID") ObjectId formDataID){
        ArrayList<Group> formDataGroups = service.getFormDataGroups(formDataID);
        return formDataGroups;
    }
    /**
     * GET /api/formdata/Group/{formDataID}/{group}
     * @param formDataID the formdata object (identified by its id)
     * @param groupName  name of the group
     * @return Group
     */
    @GetMapping("/Group")
    @ResponseBody
    public Group getGroup(@RequestParam("formDataID") ObjectId formDataID,
                          @RequestParam("groupName") String groupName){
        Group Group = service.getFormDataSpecificGroup(formDataID,groupName);
        return Group;
    }
    /**
     * POST /api/formdata/Group/post
     * @param requestBodyData contains the formdata object (identified by its id)
     *                        and the group object to add
     * @return ArrayList<Group> group list of the formdata
     */
    @PostMapping("/Group/post")
    @ResponseBody
    public ArrayList<Group> addGroup(@RequestBody JsonNode requestBodyData){
        ArrayList<Group> GroupList = service.addFormDataSpecificGroup(
                objectMapper.convertValue(requestBodyData.get("formDataID"),ObjectId.class),
                objectMapper.convertValue(requestBodyData.get("group"),Group.class));
        return GroupList;
    }
    /**
     * PATCH /api/formdata/Group/patch
     * @param requestBodyData contains the formdata object (identified by its id)
     *                        and the oldGroupName and the newGroup Object
     * @return ArrayList<Group> group list of the formdata
     */
    @PatchMapping("/Group/patch")
    @ResponseBody
    public ArrayList<Group> modifyGroup(@RequestBody JsonNode requestBodyData){
        ArrayList<Group> GroupList = service.modifyFormDataSpecificGroup(
                objectMapper.convertValue(requestBodyData.get("formDataID"),ObjectId.class),
                objectMapper.convertValue(requestBodyData.get("oldGroupName"),String.class),
                objectMapper.convertValue(requestBodyData.get("newGroup"),Group.class));
        return GroupList;
    }
    /**
     * DELETE /api/formdata/Group/delete
     * @param requestBodyData contains the formdata object (identified by its id)
     *                        and the groupName (name of the group to delete)
     * @return ArrayList<Group> group list of the formdata
     */
    @DeleteMapping("/Group/delete")
    @ResponseBody
    public ArrayList<Group> deleteGroup(@RequestBody JsonNode requestBodyData){
        ArrayList<Group> GroupList = service.deleteFormDataSpecificGroup(
                objectMapper.convertValue(requestBodyData.get("formDataID"),ObjectId.class),
                objectMapper.convertValue(requestBodyData.get("groupName"),String.class));
        return GroupList;
    }
    /**
     * GET /api/formdata/Group/Map/{formDataID}/{groupName}
     * @param formDataID the formdata object (identified by its id)
     * @param groupName name of the group
     * @return Map object
     */
    @GetMapping("/Group/Map")
    @ResponseBody
    public Map getMap(@RequestParam("formDataID") String formDataID,
                      @RequestParam("groupName") String groupName){
        Map map = service.getFormDataMap(new ObjectId(formDataID),groupName);
        return map;
    }
    /**
     * POST /api/formdata/Group/Map/post
     * @param requestBodyData contains the formdata object (identified by its id)
     *                        and the groupName (name of the group) and map object
     * @return Map object
     */
    @PostMapping("/Group/Map/post")
    @ResponseBody
    public Map addMap(@RequestBody JsonNode requestBodyData){
        Map map = service.postFormDataMap(
                objectMapper.convertValue(requestBodyData.get("formDataID"),ObjectId.class),
                objectMapper.convertValue(requestBodyData.get("groupName"),String.class),
                objectMapper.convertValue(requestBodyData.get("map"),Map.class));
        return map;
    }
    /**
     * PATCH /api/formdata/Group/Map/patch
     * @param requestBodyData contains the formdata object (identified by its id)
     *                        and the groupName (name of the group) and map object
     * @return Map object
     */
    @PatchMapping("/Group/Map/patch")
    @ResponseBody
    public Map patchMap(@RequestBody JsonNode requestBodyData){
        Map map = service.patchFormDataMap(
                objectMapper.convertValue(requestBodyData.get("formDataID"),ObjectId.class),
                objectMapper.convertValue(requestBodyData.get("groupName"),String.class),
                objectMapper.convertValue(requestBodyData.get("map"),Map.class));
        return map;
    }
    /**
     * DELETE /api/formdata/Group/Map/delete
     * @param requestBodyData contains the formdata object (identified by its id)
     *                        and the groupName (name of the group)
     * @return true if deletion was success
     */
    @DeleteMapping("/Group/Map/delete")
    @ResponseBody
    public boolean deleteMap(@RequestBody JsonNode requestBodyData){
        boolean res = service.deleteFormDataMap(
                objectMapper.convertValue(requestBodyData.get("formDataID"),ObjectId.class),
                objectMapper.convertValue(requestBodyData.get("groupName"),String.class));
        return res;
    }

    @GetMapping("/Group/Map/MaterialALL")
    @ResponseBody
    public ArrayList<Material> getMapMaterialArrayList(@RequestParam String formDataID,
                                                       @RequestParam String groupName){
        ArrayList<Material> materialArrayList = service.getFormDataMapMaterialArrayList(
                                                    objectMapper.convertValue(formDataID,ObjectId.class),groupName);
        return materialArrayList;
    }

    @GetMapping("/Group/Map/Material")
    @ResponseBody
    public Material getMapMaterial(@RequestParam String formDataID,
                                        @RequestParam String groupName,
                                            @RequestParam String materialName){
        Material material = service.getFormDataMapMaterial(objectMapper.convertValue(formDataID,ObjectId.class)
                                                                ,groupName,materialName);
        return material;
    }
    @PostMapping("/Group/Map/Material/post")
    @ResponseBody
    public ArrayList<Material> postMapMaterial(@RequestBody JsonNode requestBodyData){
        ArrayList<Material> materialArrayList = service.addFormDataMapMaterial(
                objectMapper.convertValue(requestBodyData.get("formDataID"),ObjectId.class),
                objectMapper.convertValue(requestBodyData.get("groupName"),String.class),
                objectMapper.convertValue(requestBodyData.get("material"),Material.class));
        return materialArrayList;
    }
    @DeleteMapping("/Group/Map/Material/delete")
    @ResponseBody
    public ArrayList<Material> deleteMapMaterial(@RequestBody JsonNode requestBodyData){
        ArrayList<Material> materialArrayList = service.removeFormDataMapMaterial(
                objectMapper.convertValue(requestBodyData.get("formDataID"),ObjectId.class),
                objectMapper.convertValue(requestBodyData.get("groupName"),String.class),
                objectMapper.convertValue(requestBodyData.get("materialName"),String.class));
        return materialArrayList;
    }




//    /**
//     * GET /api/formdata
//     * @return all form data in the database
//     */
//    @GetMapping("")
//    @ResponseBody
//    public List<FormData> getAllFormData() {
//        return service.getAllFormData();
//    }
//
//    /**
//     * GET /api/formdata/{prefabName}
//     * @param prefabName  name of the prefab
//     * @return all form data relating to a prefab of name prefabName
//     */
//    @GetMapping("{prefab}")
//    @ResponseBody
//    // GET all data relating to a prefab
//    public List<FormData>getAllFormDataFromPrefab(@PathVariable("prefab") String prefabName) {
//        return service.getAllFormDataFromPrefab(prefabName);
//    }
//
//    /**
//     * GET /api/formdata/{prefabName}/{groupName}/{fieldName}
//     * @param prefabName name of the prefab
//     * @param groupName name of the group belonging to the prefab
//     * @param fieldName name of the field belonging to above group
//     * @return an array of all field values relating to a single prefab.group.field combination
//     */
//    @GetMapping("{prefab}/{group}/{field}")
//    @ResponseBody
//    // GET all data relating to a single field (selector from data value)
//    public List<String> getAllFormDataFromPrefabField(@PathVariable("prefab") String prefabName,
//                                                      @PathVariable("group") String groupName,
//                                                      @PathVariable("field") String fieldName) {
//        return service.getAllFormDataFromPrefabField(prefabName, groupName, fieldName);
//    }
//
//    /**
//     * POST /api/formdata/{prefab}
//     * @param formData the data to save
//     * @return the added form data
//     */
//    @PostMapping("{prefab}")
//    @ResponseBody
//    // POST formdata related to a prefab
//    public FormData postFormData(@RequestBody FormData formData) {
//        return service.postFormData(formData);
//    }
//
//    /**
//     * PATCH/PUT /api/formdata/{prefab}
//     * @param formData the modified form data object (identified by its id)
//     * @return the modified form data object after its save
//     */
//    @RequestMapping(value = "{prefab}", method = {RequestMethod.PATCH, RequestMethod.PUT})
//    @ResponseBody
//    // PATCH/PUT formdata
//    public FormData patchFormData(@RequestBody FormData formData) {
//        return service.patchFormData(formData);
//    }
//
//    /**
//     * DELETE /api/formdata/{prefab}
//     * @param formData the form data object to delete (identified by its id)
//     * @return true if the deletion was a success
//     */
//    @DeleteMapping("{prefab}")
//    @ResponseBody
//    // DELETE formdata
//    public boolean deleteFormData(@RequestBody FormData formData) {
//        return service.deleteFormData(formData);
//    }
}
