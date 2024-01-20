package fr.utbm.da50.freelyforms.core.repository;

import fr.utbm.da50.freelyforms.core.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository that queries the database to fetch User objects in the users collection.
 * Most of the methods used are already present in the MongoRepository interface
 *
 * @author hamza91
 */
@Repository
public interface UserRepository extends MongoRepository<User,String> {

}
