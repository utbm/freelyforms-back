package fr.utbm.da50.freelyforms.core.entity.formdata;

import lombok.Data;

import java.util.ArrayList;

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
public class Map {

    /**
     * An array list to store all materials
     * */
    private ArrayList<Material> materialArrayList;

    /**
     * A map may be initialized with no data
     * */
    public Map(){
    }
    /**
     * Or initialized with data to be determined depending on front end implementation
     * */
    public Map(ArrayList<Material> materialArrayList){
        this.materialArrayList = materialArrayList;
    }

    /**
     * @return a stringified representation of the map
     */
    public String inspect() {
        StringBuilder ret = new StringBuilder("\nMap: ");
        for(Material m : materialArrayList){
            ret.append("n\t").append(m.inspect());
        }
        return ret.toString();
    }

}
