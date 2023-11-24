package fr.utbm.da50.freelyforms.core.entity.formdata;

import lombok.Data;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.UUID;

/**
 * The Map class holds data.
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
        this.materialArrayList = null;
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
