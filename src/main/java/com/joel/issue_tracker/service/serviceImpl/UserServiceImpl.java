package com.joel.issue_tracker.service.serviceImpl;

import com.joel.issue_tracker.exceptions.customExceptions.UserException;
import com.joel.issue_tracker.models.Role;
import com.joel.issue_tracker.models.User;
import com.joel.issue_tracker.models.dto.RegisterUserDTO;
import com.joel.issue_tracker.models.dto.UserProfileDTO;
import com.joel.issue_tracker.models.dto.UsersDTO;
import com.joel.issue_tracker.repo.RoleRepo;
import com.joel.issue_tracker.repo.UserRepo;
import com.joel.issue_tracker.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
            newUser.setUserId("USR-" + UUID.randomUUID().toString().substring(0,8));
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
            Role role = roleRepo.findByRoleName("ROLE_SUPPORT").orElseThrow();
            newUser.getRoles().add(role);
            userRepo.save(newUser);
            return "User registered successfully";
        }
    }

    @Override
    public List<UsersDTO> getUsers() {
        List<UsersDTO> usersDTOList = new ArrayList<>();
        List<User> users = userRepo.findAll();
        users.forEach(user -> {
            UsersDTO usersDTO = new UsersDTO();
            usersDTO.setUsername(user.getUsername());
            usersDTO.setEmail(user.getEmail());
            usersDTO.setId(user.getId());
            usersDTO.setRole(user.getRoles().iterator().next().getRoleName());
            usersDTO.setNoOfTickets(user.getCreatedTickets().size());
            usersDTO.setUserID(user.getUserId());
            usersDTOList.add(usersDTO);
        });
        return usersDTOList;
    }

    @Override
    public UserProfileDTO getUserProfile(String name) throws UserException {
        User currentUser = userRepo.findByUsername(name);
        UserProfileDTO userProfileDTO = modelMapper.map(currentUser, UserProfileDTO.class);
        userProfileDTO.setNoOfTickets(currentUser.getCreatedTickets().size());
        userProfileDTO.setRole(currentUser.getRoles().iterator().next().getRoleName());
        return userProfileDTO;
    }

    @Override
    public UsersDTO getUserByUserID(String userId) throws UserException {
        User user = userRepo.findByUserId(userId);
        UsersDTO usersDTO = modelMapper.map(user, UsersDTO.class);
        usersDTO.setRole(user.getRoles().iterator().next().getRoleName());
        usersDTO.setNoOfTickets(user.getCreatedTickets().size());
        return usersDTO;
    }

    private User convertDTOtoEntity(RegisterUserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}
