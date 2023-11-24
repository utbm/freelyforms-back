package fr.utbm.da50.freelyforms.core.entity;


import fr.utbm.da50.freelyforms.core.entity.formdata.Group;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.ArrayList;

/**
 * The FormData class holds data that has been filled by users, in its Group and Field classes ;
 * the structure of these groups and classes mirrors the structure of the associated prefab.
 *
 * Once a form is filled, the front-end app sends the result to the FormData API,
 * which turns the input into a FormData object.
 * This class is used by SpringData to identify and store FormData documents in the form_data collection.
 *
 * @author Pourriture
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("form_data")
public class FormData {

    @Id
    ObjectId _id;

    /**
     * name of the prefab to which this form data is related to
     */
    private String prefabName;
    /**
     * form data groups holding fields
     */
    ArrayList<Group> groups;

    @PersistenceCreator
    public FormData(String prefabName, ArrayList<Group> groups) {
        this.prefabName = prefabName;
        this.groups = groups;
    }

    /**
     * @return a string representing the form data
     */
    public String inspect() {
        // todo: make inspect function
        StringBuilder ret = new StringBuilder("FormData from form " + prefabName + '\n');
        for (Group g : groups) {
            ret.append(g.inspect());
            ret.append('\n');
        }
        return ret.toString();
    }


    /**
     * Incomplete method that should verify if the form data is valid regarding the values and rules set by its prefab (identified by name with prefabName)
     * @return whether this form data object is valid relating to the prefab identified by attribute prefabName
     */
    public boolean verifyDataValidity() {

        // TODO: MAKE THIS WORK
        // note: prefab of name prefabName can be fetched by calling findPrefabByName on the PrefabReposiory interface
        // note 2: ideally tho you should create a PrefabService class that acts as a middleman to perform verifications and that will query the repo itself
        // (and modify the prefabController so that it queries the service instead of the repository directly)

        return true;
    }

    /**
     * @param name the name of the group to look for
     * @return the group that has been looked for
     */
    public Group getGroup(String name){
        for (Group g : this.groups) {
            if (g.getName().equals(name)) {
                return g;
            }
        }
        return null;
    }
    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
    }
    /**
     * @param group the name of the new group
     */
    public boolean addGroup(Group group){
        System.out.println("\nthis.groups.isEmpty()" + this.groups.isEmpty() );
        if(this.groups.isEmpty()){
            this.groups.add(group);
        } else{
            boolean proceed = true;
            for(Group p : this.groups){
                System.out.println("p.getName().equals(group.getName()): " + p.getName().equals(group.getName()));
                if(p.getName().equals(group.getName())){
                    proceed = false;
                }
            }
            if(proceed){
                this.groups.add(group);
                return true;
            }
        }
        return false;
    }

}