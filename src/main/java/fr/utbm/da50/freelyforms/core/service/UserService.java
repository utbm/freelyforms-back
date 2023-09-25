package fr.utbm.da50.freelyforms.core.service;

import fr.utbm.da50.freelyforms.core.entity.User;
import fr.utbm.da50.freelyforms.core.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


/**
 * Service that acts as an interface between the repository and other classes which may which to fetch data from the database.
 * This service fetches User objects.
 *
 * @author hamza91
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * @param user a User to save
     * @return the saved User
     */
    public User saveUser(User user) {
        user.setId(UUID.randomUUID().toString().split("-")[0]);
        return userRepository.save(user); }

    /**
     * @param id the id of the user
     * @return the user
     */
    public Optional<User> getUser(String id) { return userRepository.findById(id); }

    /**
     * @param id the id of the user
     * @return the deleted user
     */
    public Optional<User> deleteUser(String id) {

        Optional<User> user = userRepository.findById(id);

        userRepository.delete(user.get());

        return user;
    }
}

