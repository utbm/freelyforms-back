package fr.utbm.da50.freelyforms.core.entity.formdata;

import lombok.Data;
import org.springframework.data.annotation.PersistenceCreator;

import java.util.UUID;

/**
 * The Location class holds data about where materials
 * are present on the map
 */
@Data
public class Location {

    /**
     * Unique Identifier
     */
    private final String id;

    /**
     * Each location has its own coordinates
     */
    private double x,y;
    /**
     * A zone is represented by a single point having a radius
     */
    private double radius;

    /**
     * Every zone has its own whereabouts
     */
    private String address;

    @PersistenceCreator
    public Location(String address,double x, double y, double radius){
        id = UUID.randomUUID().toString();
        this.address = address;
        this.radius = radius;
        this.x = x;
        this.y = y;
    }
    public Location(){
        id = UUID.randomUUID().toString();
        this.x = this.y = this.radius = 0;
        this.address = "DEFAULT_ADDRESS";
    }

    /**
     * @return TRUE if it is a point
     *         FALSE if it is a zone
     * */
    public Boolean getType(){
        return radius == 0;
    }
    /**
     * @return a stringified representation of the location
     */
    public String inspect() {
        return "address: " + address + " Coordinates(" + x + "," + y + ") radius: " + radius;
    }

}
