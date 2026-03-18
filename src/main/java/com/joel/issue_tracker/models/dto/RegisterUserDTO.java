package com.joel.issue_tracker.models.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterUserDTO {
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;
}
