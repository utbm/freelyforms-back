package fr.utbm.da50.freelyforms.core.entity.formdata;

import lombok.*;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Field
{


    /**
     * Name of the field, unique within a Group object and mirroring a field name in the related Prefab.Group object
     */
    @Builder.Default
    @NonNull
    private String name = "";
    /**
     * Value of the field, entered by a user
     */
    @Builder.Default
    @NonNull
    private String value = "";


    /**
     * @return a stringified representation of the field
     */
    public String inspect() {
        return name + " " + value;
    }

}
