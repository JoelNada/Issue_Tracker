package com.joel.issue_tracker.config;

import com.joel.issue_tracker.models.Role;
import com.joel.issue_tracker.models.User;
import com.joel.issue_tracker.repo.RoleRepo;
import com.joel.issue_tracker.repo.UserRepo;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;

@Order(2)
@Component
public class AdminInitializer implements CommandLineRunner {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String @NonNull ... args) throws Exception {

        if (!userRepo.existsByEmail("admin@support.com")) {
            Role role = roleRepo.findByRoleName("ROLE_ADMIN").orElseThrow();
            User newUser = new User();
                   newUser.setUsername("System-Admin");
                   newUser.setEmail("admin@support.com");
                   newUser.setPassword(passwordEncoder.encode("admin@123"));
                   newUser.setRoles(Set.of(role));
                   newUser.setUserId("USR-" + UUID.randomUUID().toString().substring(0,8));
                   newUser.setDateOfBirth("2000-10-20");
                   userRepo.save(newUser);
        }
    }
}
