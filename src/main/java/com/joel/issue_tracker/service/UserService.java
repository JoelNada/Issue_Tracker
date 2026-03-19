package com.joel.issue_tracker.service;


import com.joel.issue_tracker.exceptions.customExceptions.UserException;

import com.joel.issue_tracker.models.dto.RegisterUserDTO;
import com.joel.issue_tracker.models.dto.UserProfileDTO;
import com.joel.issue_tracker.models.dto.UsersDTO;

import java.util.*;

public interface UserService {

    public String registerSupportAgent(RegisterUserDTO userDTO) throws UserException;
    public List<UsersDTO> getUsers();
    public UserProfileDTO getUserProfile(String name) throws UserException;
    public UsersDTO  getUserByUserID(String userId) throws UserException;

}
