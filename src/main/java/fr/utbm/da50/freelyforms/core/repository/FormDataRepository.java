package fr.utbm.da50.freelyforms.core.repository;


import fr.utbm.da50.freelyforms.core.entity.FormData;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Repository that queries the database to fetch FormData objects in the form_data collection.
 * Most of the methods used are already present in the MongoRepository interface
 *
 * @author Pourriture
 */
public interface FormDataRepository extends MongoRepository<FormData, ObjectId> {

    /**
     * @param prefabName the name of the prefab whose form data is being searched for
     * @return all form data relating to the prefab identified by prefabName
     */
    @Query("{prefabName:'?0'}")
    List<FormData> findFormDataByPrefabName(String prefabName);

}
