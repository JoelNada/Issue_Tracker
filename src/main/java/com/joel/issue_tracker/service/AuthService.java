package com.joel.issue_tracker.service;

import com.joel.issue_tracker.exceptions.customExceptions.UserException;
import com.joel.issue_tracker.models.dto.AuthLoginRequestDTO;
import com.joel.issue_tracker.models.dto.RegisterUserDTO;

public interface AuthService {
    public String registerUser(RegisterUserDTO userDTO) throws UserException;
    public String login(AuthLoginRequestDTO userDTO) ;
}
