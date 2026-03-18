package com.joel.issue_tracker.models.dto;

import lombok.Data;

@Data
public class AuthLoginRequestDTO {
    private String username;
    private String password;
}
