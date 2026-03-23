package com.joel.issue_tracker.controller;

import com.joel.issue_tracker.exceptions.customExceptions.UserException;
import com.joel.issue_tracker.models.dto.RegisterUserDTO;
import com.joel.issue_tracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPPORT')")
    @GetMapping("/get-users")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/admin/create-support-agent")
    public ResponseEntity<String> createUserSupport(@RequestBody @Valid RegisterUserDTO userDTO) throws UserException {
     return ResponseEntity.ok().body(userService.registerSupportAgent(userDTO));
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPPORT')")
    @GetMapping("/get-user-by-id")
    public ResponseEntity<?> getUserById(@RequestParam("userId") String userId) throws UserException {
     return ResponseEntity.ok().body(userService.getUserByUserID(userId));
    }

    @GetMapping("/get-current-user-profile")
    public ResponseEntity<?> getCurrentUserProfile(@AuthenticationPrincipal UserDetails userDetails) throws UserException {
        return ResponseEntity.ok().body(userService.getUserProfile(userDetails.getUsername()));
    }



}
