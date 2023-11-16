package fr.utbm.da50.freelyforms.core.service;

import fr.utbm.da50.freelyforms.core.entity.User;
import fr.utbm.da50.freelyforms.core.exception.user.UserNotFoundException;
import fr.utbm.da50.freelyforms.core.repository.UserRepository;
import lombok.NonNull;
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
        return userRepository.save(user);
    }

    /**
     * @param id the id of the user
     * @return the user
     */
    @NonNull
    public User getUser(@NonNull String id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
            throw new UserNotFoundException("User " + id + " not found.");
        return user.get();

    }

    /**
     * @param id the id of the user
     * @return the deleted user
     */
    public void deleteUser(String id) throws UserNotFoundException {

        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
            throw new UserNotFoundException("User " + id + " not found.");
        userRepository.delete(user.get());
    }
}

