package fr.utbm.da50.freelyforms.core.controller;

import fr.utbm.da50.freelyforms.core.entity.User;
import fr.utbm.da50.freelyforms.core.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


/**
 * Controller for the User API
 * Answers requests to the /api/users/ route
 *
 * @author hamza91
 */
@RestController
@RequestMapping(path = "api/users")
@CrossOrigin(origins = "http://localhost:5173") // Replace with the origin of your React app
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
    public Optional<User> getUser(@PathVariable String id){
        return userService.getUser(id);
    }


    /** POST /api/users
     * @param user a User to save
     * @return the saved User
     */
    @PostMapping()
    public User saveUser(@RequestBody User user) {

        return userService.saveUser(user); }

    /** DELETE /api/users/{id}
     * @param id the id of the user
     * @return the deleted user
     */
    @DeleteMapping(path="/{id}")
    public Optional<User> deleteUser(@PathVariable String id) {return userService.deleteUser(id); }
}
