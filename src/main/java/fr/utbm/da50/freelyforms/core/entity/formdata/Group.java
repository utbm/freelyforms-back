package fr.utbm.da50.freelyforms.core.entity.formdata;

import lombok.Data;
import org.springframework.data.annotation.PersistenceCreator;

import java.util.ArrayList;

/**
 * The Group class holds the fields containing data, in a structure mirroring the group of the same name
 * in the Prefab class that links to the holding FormData.
 * <p>
 *
 * Note: the other entity of this name in the prefab package is related to form configuration,
 * whereas this one is related to data holding
 *
 */
@Data
public class Group {
    /**
     * Name of the group, unique within a FormData object and mirroring a group name in the related Prefab object
     */
    private String name;
    /**
     * Each group has a map
     * */
    private  Map map;
    /**
     * Fields of the group, mirroring group fields in the related Prefab object
     */
    private ArrayList<Field> fields;

    @PersistenceCreator
    public Group(String name, ArrayList<Field> fields, Map map) {
        this.name = name;
        this.fields = fields;
        this.map = map;
    }
    public Group(){
        this.name = "DEFAULT_NAME";
        this.fields = null;
        this.map = null;
    }

    /**
     * @return a stringified representation of the group
     */
    public String inspect() {
        StringBuilder ret = new StringBuilder("\nGroup[" + name + "]:");
        for (Field f : fields){
            ret.append("\n\t").append(f.inspect());
        }
        ret.append(map.inspect());
        return ret.toString();
    }

    /**
     * @param name the name of the field to look for
     * @return the field that has been looked for
     */
    public Field getField(String name){
        for (Field f : this.fields) {
            if (f.getName().equals(name)) {
                return f;
            }
        }
       return  null;
    }

    /**
     * @param name the name of the new field
     * @param value the value of this field
     *              Note that all values are converted to strings in the JSON and BSON formats,
     *              so it will be up to the verification methods to attempt to convert the values in their expected types
     */
    public void addField(String name, String value) {
        this.fields.add(new Field(name, value));
    }


}
