package fr.utbm.da50.freelyforms.core.entity.formdata;

import lombok.Data;
import org.springframework.data.annotation.PersistenceCreator;

import java.util.ArrayList;
import java.util.UUID;


/**
 * The Material class holds the data. Its name mirrors the name of a field in the related Prefab class
 * <p>
 *
 * Note: the other entity of this name in the prefab package is related to form configuration,
 * whereas this one is related to data holding
 * </p>
 *
 * @author illuminatumSolis
 */
@Data
public class Material {
    /**
     * Each material has a unique Identifier
     * */
    private final String _id;
    /**
     * Each material has a unique name
     * */
    private String name;
    /**
     * Different types of material exist
     * */
    private String type;
    /**
     * Each material has its own unique in order to visually distinguish them
     * */
    private String color;
    /**
     * A material can be located at different places
     * */
    protected ArrayList<Location> locationArrayList;

    @PersistenceCreator
    public Material(String name, String type, String color, ArrayList<Location> locationArrayList){
        _id = UUID.randomUUID().toString();
        this.name = name;
        this.type = type;
        this.color = color;
        this.locationArrayList = locationArrayList;
    }

    public Material(){
        this._id = UUID.randomUUID().toString();
        this.name="DEFAULT_NAME";
        this.type = "DEFAULT_TYPE";
        this.color = "#000000";
        this.locationArrayList = null;
    }
    /**
     * @return a stringified representation of the material and its locations
     */
    public String inspect() {
        StringBuilder ret = new StringBuilder("\nMaterial[").append(name).append("]: ");
        int i=0;
        for(Location l : locationArrayList){
            ret.append("\n\t").append("location[").append(i).append("]:").append(l.inspect());
            i++;
        }
        return ret.toString();
    }

}
