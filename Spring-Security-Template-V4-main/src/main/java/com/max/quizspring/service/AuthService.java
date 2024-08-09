package com.max.quizspring.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.max.quizspring.auth.LoginRequest;
import com.max.quizspring.auth.RegisterRequest;
import com.max.quizspring.auth.UpdateRequest;
import com.max.quizspring.config.JwtToken;
import com.max.quizspring.model.Token;
import com.max.quizspring.model.User;
import com.max.quizspring.repo.JwtRepo;
import com.max.quizspring.repo.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepository;
    private final JwtRepo tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtToken jwtUtil;

    public String register(RegisterRequest registerRequest) {
        Optional<User> userExist = userRepository.findByUsername(registerRequest.getUsername());
        if (userExist.isPresent()) {
            return "User already exists with username " + registerRequest.getUsername();
        }
        var user = User.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .username(registerRequest.getUsername())
                .role(User.Role.USER)
                .build();
        userRepository.save(user);
        return "User registered successfully.";
    }

    private void saveUserToken(User user, String accessToken) {
        var token = Token.builder().user(user).token(accessToken).expired(false).revoked(false).build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllByUser_UidAndExpiredFalseAndRevokedFalse(user.getUid());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public String createAdmin() {
        Optional<User> userExist = userRepository.findByUsername("admin");
        if (userExist.isPresent()) {
            return "Admin already exists";
        }

        var user = User.builder()
                .name("Admin")
                .email("admin@gmail.com")
                .password(passwordEncoder.encode("Admin@123"))
                .username("admin")
                .role(User.Role.ADMIN)
                .build();
        userRepository.save(user);
        return "Admin registered successfully.";
    }

    public Map<String, Object> login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
    
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);  // Make sure JWT generation is set up for UserDetails
    
        Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }
    
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("role", user.get().getRole().name());
    
        return response;
    }
    public void deleteUser(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else {
            throw new RuntimeException("User not found with ID: " + userId);
        }
    }
    
    
    public Object updateUser(UpdateRequest updateRequest) {
        // Implement user update logic here
        throw new UnsupportedOperationException("Unimplemented method 'updateUser'");
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
