package com.joel.issue_tracker.controller;

import com.joel.issue_tracker.exceptions.customExceptions.UserException;
import com.joel.issue_tracker.models.dto.AuthLoginRequestDTO;
import com.joel.issue_tracker.models.dto.RegisterUserDTO;
import com.joel.issue_tracker.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register-user")
    public ResponseEntity<String> createUser(@RequestBody @Valid RegisterUserDTO userDTO) throws UserException {
        return ResponseEntity.ok().body(authService.registerUser(userDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthLoginRequestDTO authLoginRequestDTO) {
        Map<String, String> token = new HashMap<>();
        token.put("token", authService.login(authLoginRequestDTO));
        return ResponseEntity.ok().body(token);
    }


}
