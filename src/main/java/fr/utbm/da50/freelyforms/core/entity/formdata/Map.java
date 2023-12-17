package fr.utbm.da50.freelyforms.core.entity.formdata;

import lombok.Data;

import java.util.ArrayList;

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

    public Material getMaterial(String materialName){
        for(Material m : this.materialArrayList){
            if (m.getName().equals(materialName)){
                return m;
            }
        }
        return null;
    }
    public ArrayList<Material> addMaterial(Material material){
        if(this.materialArrayList.isEmpty()){
            this.materialArrayList.add(material);
        } else{
            boolean proceed = true;
            for(Material m : this.materialArrayList){
                if (m.getName().equals(material.getName())) {
                    proceed = false;
                    break;
                }
            }
            if(proceed){
                this.materialArrayList.add(material);
            }
        }
        return this.materialArrayList;
    }

}
