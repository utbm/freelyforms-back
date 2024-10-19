package com.utbm.da50.freelyform.seeder;

import com.utbm.da50.freelyform.enums.UserRole;
import com.utbm.da50.freelyform.model.User;
import com.utbm.da50.freelyform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class UserSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        seedAdminUser();
    }

    private void seedAdminUser() {
        String adminEmail = "admin@freelyform.com";

        // Check if an admin user already exists
        boolean adminExists = userRepository.existsByEmail(adminEmail);

        if (!adminExists) {
            // Create the admin user
            User adminUser = new User();
            adminUser.setFirstName("Default");
            adminUser.setLastName("Admin");
            adminUser.setEmail(adminEmail);
            adminUser.setPassword(passwordEncoder.encode("admin123"));
            adminUser.setRole(new HashSet<>(Collections.singletonList(UserRole.ADMIN)));

            userRepository.save(adminUser);
            System.out.println("Admin user created: " + adminEmail);
        } else {
            System.out.println("Admin user already exists: " + adminEmail);
        }
    }
}

