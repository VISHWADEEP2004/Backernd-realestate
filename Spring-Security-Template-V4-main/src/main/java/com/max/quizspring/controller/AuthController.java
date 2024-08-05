package com.max.quizspring.controller;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.max.quizspring.auth.LoginRequest;
import com.max.quizspring.auth.RegisterRequest;
import com.max.quizspring.auth.UpdateRequest;
import com.max.quizspring.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user authentication")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Allows users to register by providing necessary registration details.")
    public ResponseEntity<?> register(@Parameter(description = "Registration details of the user") @RequestBody RegisterRequest registerRequest) {
        return new ResponseEntity<>(authService.register(registerRequest), OK);
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate user", description = "Allows users to authenticate by providing valid login credentials.")
    public ResponseEntity<?> login(@Parameter(description = "Login credentials of the user") @RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(authService.login(loginRequest), OK);
    }
    @PutMapping("/update")
    @Operation(summary = "Update user information", description = "Allows users to update their information by providing updated details.")
    public ResponseEntity<?> update(@Parameter(description = "Updated details of the user") @RequestBody UpdateRequest updateRequest) {
        return new ResponseEntity<>(authService.updateUser(updateRequest), OK);
    }

    @DeleteMapping("/delete/{userId}")
    @Operation(summary = "Delete a user", description = "Allows users to delete their account by providing a user ID.")
    public ResponseEntity<?> delete(@Parameter(description = "ID of the user to be deleted") @PathVariable Long userId) {
        authService.deleteUser(userId);
        return new ResponseEntity<>(NO_CONTENT);
    }
}
