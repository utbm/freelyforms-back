package fr.utbm.da50.freelyforms.core.entity.formdata;

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


    /**
     * @return a stringified representation of the field
     */
    public String inspect() {
        return name + " " + value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @PersistenceCreator
    public Field(String name, String value) {
        this.name = name;
        this.value = value;
    }

}
