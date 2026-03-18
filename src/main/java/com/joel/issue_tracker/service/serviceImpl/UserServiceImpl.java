package com.joel.issue_tracker.service.serviceImpl;

import com.joel.issue_tracker.exceptions.customExceptions.UserException;
import com.joel.issue_tracker.models.Role;
import com.joel.issue_tracker.models.User;
import com.joel.issue_tracker.models.dto.RegisterUserDTO;
import com.joel.issue_tracker.repo.RoleRepo;
import com.joel.issue_tracker.repo.UserRepo;
import com.joel.issue_tracker.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public String registerSupportAgent(RegisterUserDTO userDTO) throws UserException {
        if(userRepo.existsByEmail(userDTO.getEmail())) {
            throw new UserException("Email already exists");
        }
        else{
            User newUser = convertDTOtoEntity(userDTO);
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
            Role role = roleRepo.findByRoleName("ROLE_SUPPORT").orElseThrow();
            newUser.getRoles().add(role);
            userRepo.save(newUser);
            return "User registered successfully";
        }
    }

    private User convertDTOtoEntity(RegisterUserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}
