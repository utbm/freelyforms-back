package com.utbm.da50.freelyform.service;


import com.utbm.da50.freelyform.dto.user.UpdateUserRequest;
import com.utbm.da50.freelyform.model.User;
import com.utbm.da50.freelyform.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    // Business logic in service
    private final UserRepository userRepository;

    // Dependency injection with auto-wiring
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll(Optional<Integer> limit, Optional<Integer> offset) {
        return userRepository.findAll();
    }

    public User getUserById(@NonNull Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with ID '" + userId + "' doesn't exist."));
    }

    public User updateUser(@NonNull Integer userId, UpdateUserRequest user) {
        User userToUpdate = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with ID '" + userId + "' doesn't exist."));

        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());

        return userRepository.save(userToUpdate);
    }

    public void deleteUser(@NonNull Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User with ID '" + userId + "' doesn't exist");
        }

        userRepository.deleteById(userId);
    }

    public int count() {
        return (int) userRepository.count();
    }

    public List<User> getUsersByIds(List<Integer> ids) {
        List<User> users = new ArrayList<>();
        for (Integer id : ids) {
            users.add(userRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("User with ID '" + id + "' doesn't exist")));
        }
        return users;
    }

}
