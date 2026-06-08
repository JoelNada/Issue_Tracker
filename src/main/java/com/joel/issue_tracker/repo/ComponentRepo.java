package com.joel.issue_tracker.repo;

import com.joel.issue_tracker.models.ComponentModel;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComponentRepo extends JpaRepository<ComponentModel, Long> {
   Optional <ComponentModel> findByComponentId(@NotNull(message = "Component is required") String componentId);
}
