package com.joel.issue_tracker.service.serviceImpl;

import com.joel.issue_tracker.exceptions.customExceptions.UserException;
import com.joel.issue_tracker.models.*;
import com.joel.issue_tracker.models.dto.*;
import com.joel.issue_tracker.repo.*;
import com.joel.issue_tracker.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public UserProfileDTO getUserProfile( ) throws UserException {
        //System.out.println(name);
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
        assert auth != null;
        Object principal = auth.getPrincipal();
        UserPrincipal userPrincipal = (UserPrincipal) principal;
        assert userPrincipal != null;
        System.out.println("Test from UserServiceImpl : "+userPrincipal.getUsername());
        User currentUser = userRepo.findByEmail(userPrincipal.getUsername());
        UserProfileDTO userProfileDTO = new UserProfileDTO();
        userProfileDTO.setId(currentUser.getId());
        userProfileDTO.setUsername(currentUser.getUsername());
        userProfileDTO.setEmail(currentUser.getEmail());
        userProfileDTO.setUserID(currentUser.getUserId());
        List<TicketViewDTO> ticketViewDTOs = new ArrayList<>();
        currentUser.getCreatedTickets().forEach(ticket -> {
            TicketViewDTO ticketViewDTO = new TicketViewDTO();
            TicketComponentDTO ticketComponentDTO = new TicketComponentDTO();
            ticketViewDTO.setId(ticket.getId());
            ticketViewDTO.setTitle(ticket.getTitle());
            ticketViewDTO.setDescription(ticket.getDescription());
            ticketViewDTO.setCreatedAt(ticket.getCreatedAt());
            ticketViewDTO.setStatus(ticket.getStatus());
            ticketViewDTO.setPriority(ticket.getPriority());
            ticketViewDTO.setCreatedBy(ticket.getCreatedBy().getUsername());
            ticketViewDTO.setAssignedTo(ticket.getAssignedTo()!=null ? ticket.getAssignedTo().getUsername():"Unassigned");
            ticketComponentDTO.setComponentId(ticket.getComponent().getComponentId());
            ticketComponentDTO.setComponentName(ticket.getComponent().getComponentName());
            ticketComponentDTO.setId(ticket.getComponent().getId());
            ticketViewDTO.setTicketComponent(ticketComponentDTO);
            ticketViewDTO.setUpdatedAt(ticket.getUpdatedAt());
            ticketViewDTOs.add(ticketViewDTO);
        });

        userProfileDTO.setNoOfTickets(currentUser.getCreatedTickets().size());
        userProfileDTO.setRole(currentUser.getRoles().iterator().next().getRoleName());
        userProfileDTO.setTickets(ticketViewDTOs);
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
