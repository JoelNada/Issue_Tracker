package com.joel.issue_tracker.repo;

import com.joel.issue_tracker.models.Role;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role,Long> {

    @NullMarked
    Optional<Role> findById(Long id);
    Optional<Role> findByRoleName(String name);
}
