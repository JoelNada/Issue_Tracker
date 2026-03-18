package com.joel.issue_tracker.repo;

import com.joel.issue_tracker.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);
    User findByUsername(String username);
}
