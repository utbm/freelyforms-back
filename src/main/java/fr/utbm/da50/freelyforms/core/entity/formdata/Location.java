package fr.utbm.da50.freelyforms.core.entity.formdata;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.PersistenceCreator;

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
@AllArgsConstructor
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

    /**
     * returns TRUE if it is a single point otherwise returns FALSE
     */
    @PersistenceCreator
    public Location(String address,double x, double y, double radius){
        id = UUID.randomUUID().toString();
        this.address = address;
        this.radius = radius;
        this.x = x;
        this.y = y;
    }

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
