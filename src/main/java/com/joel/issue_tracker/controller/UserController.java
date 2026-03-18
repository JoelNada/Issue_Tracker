package com.joel.issue_tracker.controller;

import com.joel.issue_tracker.exceptions.customExceptions.UserException;
import com.joel.issue_tracker.models.dto.RegisterUserDTO;
import com.joel.issue_tracker.service.AuthService;
import com.joel.issue_tracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<String> helloWorld(){
        return ResponseEntity.ok("Hello World");
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/admin/create-support-agent")
    public ResponseEntity<String> createUserSupport(@RequestBody @Valid RegisterUserDTO userDTO) throws UserException {
     return ResponseEntity.ok().body(userService.registerSupportAgent(userDTO));
    }
}
