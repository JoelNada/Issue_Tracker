package com.joel.issue_tracker.config;

import com.joel.issue_tracker.models.Role;
import com.joel.issue_tracker.repo.RoleRepo;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.*;

@Order(1)
@Component
public class RoleInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public void run(String @NonNull ... args) throws Exception {
        List<String> roles = Arrays.asList("ROLE_ADMIN", "ROLE_USER", "ROLE_SUPPORT");

        for(String role:roles){
            if(roleRepo.findByRoleName(role).isEmpty()){
                Role role1 = Role.builder().roleName(role).build();
                roleRepo.save(role1);
                System.out.printf("Role has been added to roles: %s\n",role);
            }
        }
    }
}
