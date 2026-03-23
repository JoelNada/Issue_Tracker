package com.joel.issue_tracker.service.serviceImpl;

import com.joel.issue_tracker.exceptions.customExceptions.UserException;
import com.joel.issue_tracker.models.Role;
import com.joel.issue_tracker.models.User;
import com.joel.issue_tracker.models.dto.AuthLoginRequestDTO;
import com.joel.issue_tracker.models.dto.RegisterUserDTO;
import com.joel.issue_tracker.repo.RoleRepo;
import com.joel.issue_tracker.repo.UserRepo;
import com.joel.issue_tracker.service.AuthService;
import com.joel.issue_tracker.service.JWTService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public String registerUser(RegisterUserDTO userDTO) throws UserException {
        if(userRepo.existsByEmail(userDTO.getEmail())) {
            throw new UserException("Email already exists");
        }
        else{
            User newUser = convertDTOtoEntity(userDTO);
            newUser.setUserId("USR-" + UUID.randomUUID().toString().substring(0,8));
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
            Role role = roleRepo.findByRoleName("ROLE_USER").orElseThrow();
            newUser.getRoles().add(role);
            userRepo.save(newUser);
            return "User registered successfully";
        }
    }

    @Override
    public String login(AuthLoginRequestDTO userDTO) {
        Authentication authentication = manager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        userDTO.getEmail(),
                        userDTO.getPassword())
                );
        if(authentication.isAuthenticated()){
            UserDetails userDetails = userDetailsService.loadUserByUsername(userDTO.getEmail());
            //System.out.println("The credentials were right!");
            return jwtService.generateToken(userDetails);

        }
        else {
            System.out.println("Invalid username or password");
            return "Wrong username or password";
        }
    }


    private User convertDTOtoEntity(RegisterUserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}
