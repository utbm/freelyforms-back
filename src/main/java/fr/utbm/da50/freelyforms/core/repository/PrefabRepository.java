package fr.utbm.da50.freelyforms.core.repository;

import fr.utbm.da50.freelyforms.core.entity.Prefab;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Repository that queries the database to fetch Prefab objects in the Prefab collection.
 * Most of the methods used are already present in the MongoRepository interface
 *
 * @author Pourriture
 */
public interface PrefabRepository extends MongoRepository<Prefab, String> {


    /**
     * @param name the name of the prefab
     * @return the unique prefab whose name matches the parameter
     */
    @Query("{name:'?0'}.limit(1)")
    Prefab findPrefabByName(String name);

    @Query(value="{category:'?0'}", fields="{'name' : 1, 'quantity' : 1}")
    List<Prefab> findAll(String category);

}
