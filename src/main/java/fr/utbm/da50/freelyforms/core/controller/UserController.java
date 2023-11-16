package fr.utbm.da50.freelyforms.core.controller;

import com.sun.istack.NotNull;
import fr.utbm.da50.freelyforms.core.entity.User;
import fr.utbm.da50.freelyforms.core.exception.user.UserNotFoundException;
import fr.utbm.da50.freelyforms.core.service.UserService;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


/**
 * Controller for the User API
 * Answers requests to the /api/users/ route
 *
 * @author hamza91
 */
@RestController
@RequestMapping(path = "api/users")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }


    /** GET /api/users/{id}
     * @param id a UserId to fetch
     * @return the fetched user
     */
    @GetMapping(path = "/{id}")
    public User getUser(@NonNull @NotNull @PathVariable String id){
        try {
            return userService.getUser(id);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, e.getMessage());
        }
    }


    /** POST /api/users
     * @param user a User to save
     * @return the saved User
     */
    @PostMapping()
    public User saveUser(@NonNull @NotNull @RequestBody User user) {
        return userService.saveUser(user);
    }

    /** DELETE /api/users/{id}
     * @param id the id of the user
     */
    @DeleteMapping(path="/{id}")
    public void deleteUser(@NonNull @NotNull @PathVariable String id) {
        try {
            userService.deleteUser(id);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
