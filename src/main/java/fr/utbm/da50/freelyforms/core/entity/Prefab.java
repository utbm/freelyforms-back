package fr.utbm.da50.freelyforms.core.entity;


import fr.utbm.da50.freelyforms.core.entity.prefab.Group;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


/**
 * The Prefab class defines and structures forms to be filled in by end users.
 * Prefabs are used by the front-end app to graphically generate forms to be filled.
 *
 * The Prefab class also has tools that verify its own validity, or the validity of data that has been sent to the backend through the FormData API.
 *
 * This class is used by SpringData to identify and store Prefab documents in the prefabs collection.
 *
 * @author Pourriture
 */
@Data
@AllArgsConstructor
@Document("prefabs")
public class Prefab {

    //The Prefab class fetches form configurations from the database, and may also add a configuration file.
    //The Prefab class verifies that the form configurations are correctly structured when they are retrieved or added.
    //The Prefab class returns an error if it can't find a configuration file matching the supplied name/argument/etc


    // Explicitely defining object ID seems to enable the database to recognize updated versions of an object
    @Id
    ObjectId _id;

    /** Name of the prefab. Should be unique within the collection.
     * */
    private String name;

    /** Prefab display label on the frontend
     * */
    private String label;

    /** Mouse-over text over the prefab
     * */
    private String caption;

    /** A group holds one or more form fields and ensures they will be grouped together.
     * Group names are identifying within the array and therefore unique within the groups.
     * */
    private ArrayList<Group> groups;

    @PersistenceCreator
    public Prefab(String name, String label, String caption,  ArrayList<Group> groups) {
        super();
        this.name = name;
        this.caption = caption;
        this.label = label;
        this.groups = groups;

    }

    // Test constructor
    public Prefab() {
        super();
        this.name = "defaultName";
        this.caption = "defaultCaption";
        this.label = "defaultLabel";
        this.groups = new ArrayList<>();
        this.groups.add(new Group());
        this.groups.add(new Group());

    }


    public Prefab copyPrefab(Prefab cp) {
        this.name = cp.name;
        this.caption = cp.caption;
        this.label = cp.label;
        this.groups = cp.groups;
        return this;
    }


    /**
     * @return a string representing the prefab
     */
        public String inspect() {
        StringBuilder groutput = new StringBuilder();
        for (Group g : groups){
            groutput.append(g.inspect());
        }
        return name + " " + label + '\n' + caption + groutput;
    }

    /** Verifies that the current state of a prefab and its groups is valid.
     * Also calls verifyGroupValidity on its groups
     * @return true if the prefab is valid, false if it is not
     * */
    public Boolean verifyPrefabValidity() {
        // call this on save of prefab
        // verifies that a group has its members, that no two of its fields share a name, and that those fields are valid themselves
        boolean check = true;

        if (name == null || label == null || caption == null)
            check = false;

        Set<String> groupNames = new HashSet<>();
        for (Group g : groups) {
            if (!groupNames.add(g.getName())){
                // Set.add returns false if the set does not change (duplicate entry)
                System.out.println("At least one duplicate entry in group list for prefab " + this.name);
                check = false;
            }
            if (!g.verifyGroupValidity()){
                check = false;
                System.out.println("Group " + g.getName() + " invalid");
            }
        }
        return check;
    }

    /** Verifies that the sent data is accurate to the rules of the prefab
     * Incomplete in this version
     * @param data a FormData object to check
     * @return true if the formdata matches the prefab rules
     * */
    public boolean verifyFormData(FormData data) {
        // todo : finish this method

        if (name != data.getPrefabName()) {
            return false;
        }

        // to do - check that form field data matches rules

        // to do - check that exclude conditions are respected

        return true;
    }

    // public getters and setters seem to be necessary for springdata to correctly extract data from the database

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<Group> groups) {
        ArrayList<Group> previousGroups = this.groups;
        this.groups = groups;
        if (!verifyPrefabValidity()) {
            System.out.println("setGroups() failed: Prefab is invalid");
            this.groups = (previousGroups != null ? previousGroups : new ArrayList<>());
        }
    }
}
