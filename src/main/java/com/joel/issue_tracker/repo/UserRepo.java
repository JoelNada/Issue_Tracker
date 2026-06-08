package com.joel.issue_tracker.repo;

import com.joel.issue_tracker.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);
    User findByUsername(String username);
    User findByUserId(String userId);
    User findByEmail(String email);
    List<User> findAllByRolesRoleName(String roleName);
    @Query("""
       SELECT u
       FROM User u
       JOIN u.roles r
       WHERE r.roleName = :roleName
       """)
    List<User> findUsersByRole(
            @Param("roleName") String roleName);
}
