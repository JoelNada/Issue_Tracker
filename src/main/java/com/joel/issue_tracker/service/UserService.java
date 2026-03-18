package com.joel.issue_tracker.service;


import com.joel.issue_tracker.exceptions.customExceptions.UserException;
import com.joel.issue_tracker.models.dto.RegisterUserDTO;

public interface UserService {

    public String registerSupportAgent(RegisterUserDTO userDTO) throws UserException;


}
