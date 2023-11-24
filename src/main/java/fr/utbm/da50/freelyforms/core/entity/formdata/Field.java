package fr.utbm.da50.freelyforms.core.entity.formdata;

import lombok.Data;
import org.springframework.data.annotation.PersistenceCreator;


/**
 * The Field class holds the data. Its name mirrors the name of a field in the related Prefab class
 * <p>
 *
 * Note: the other entity of this name in the prefab package is related to form configuration,
 * whereas this one is related to data holding
 * </p>
 *
 * @author Pourriture
 */
@Data
public class Field
{
    /**
     * Name of the field, unique within a Group object and mirroring a field name in the related Prefab.Group object
     */
    private String name;
    /**
     * Value of the field, entered by a user
     */
    private String value;

    @PersistenceCreator
    public Field(String name, String value) {
        this.name = name;
        this.value = value;
    }
    public Field(){
        this.name = "DEFAULT_NAME";
        this.value = "DEFAULT_VALUE";
    }
    /**
     * @return a stringified representation of the field
     */
    public String inspect() {
        return name + " " + value;
    }

}
