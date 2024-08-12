// package com.max.quizspring.service;

// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;
// import com.max.quizspring.auth.AgentLoginRequest;
// import com.max.quizspring.auth.RegisterAgentRequest;
// import com.max.quizspring.config.JwtToken;
// import com.max.quizspring.model.Agent;
// import com.max.quizspring.model.Token;
// import com.max.quizspring.repo.AgentRepo;
// import com.max.quizspring.repo.JwtRepo;
// import lombok.RequiredArgsConstructor;

// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
// import java.util.Optional;

// @Service
// @RequiredArgsConstructor
// public class AgentService {

//     private final AgentRepo agentRepository;
//     private final JwtRepo tokenRepository;
//     private final PasswordEncoder passwordEncoder;
//     private final AuthenticationManager authenticationManager;
//     private final JwtToken jwtUtil;

//     public String registerAgent(RegisterAgentRequest registerRequest) {
//         Optional<Agent> agentExist = agentRepository.findByEmail(registerRequest.getEmail());
//         if (agentExist.isPresent()) {
//             return "Agent already exists with email id " + registerRequest.getEmail();
//         }
//         var agent = Agent.builder()
//                 .name(registerRequest.getName())
//                 .email(registerRequest.getEmail())
//                 .password(passwordEncoder.encode(registerRequest.getPassword()))
//                 .phone(registerRequest.getPhone())
//                 .role(Agent.Role.AGENT)
//                 .build();
//         agentRepository.save(agent);
//         return "Agent registered successfully.";
//     }

//     public Map<String, Object> login(AgentLoginRequest loginRequest) {
//         try {
//             Authentication authentication = authenticationManager.authenticate(
//                 new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
//             );
            
//             UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//             String token = jwtUtil.generateToken(userDetails);
            
//             Optional<Agent> agent = agentRepository.findByEmail(loginRequest.getEmail());
//             if (agent.isEmpty()) {
//                 throw new RuntimeException("Agent not found");
//             }
            
//             saveAgentToken(agent.get(), token);
            
//             Map<String, Object> response = new HashMap<>();
//             response.put("token", token);
//             response.put("role", agent.get().getRole().name());
//             response.put("agentId", agent.get().getId());
            
//             return response;
//         } catch (Exception e) {
//             throw new RuntimeException("An error occurred during login: " + e.getMessage(), e);
//         }
//     }
    
//     private void saveAgentToken(Agent agent, String accessToken) {
//         Token token = Token.builder()
//                 .agent(agent)
//                 .token(accessToken)
//                 .expired(false)
//                 .revoked(false)
//                 .build();
//         tokenRepository.save(token);
//     }

//     public void revokeAllAgentTokens(Agent agent) {
//         List<Token> validAgentTokens = tokenRepository.findAllByAgent_IdAndExpiredFalseAndRevokedFalse(agent.getId());
//         if (validAgentTokens.isEmpty()) return;
//         validAgentTokens.forEach(token -> {
//             token.setExpired(true);
//             token.setRevoked(true);
//         });
//         tokenRepository.saveAll(validAgentTokens);
//     }

//     public Optional<Agent> getAgentById(Long id) {
//         return agentRepository.findById(id);
//     }

//     public List<Agent> getAllAgents() {
//         return agentRepository.findAll();
//     }
    
//     public boolean deleteAgentById(Long id) {
//         Optional<Agent> agent = agentRepository.findById(id);
//         if (agent.isPresent()) {
//             agentRepository.deleteById(id);
//             return true;
//         }
//         return false;
//     }
// }

package com.max.quizspring.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.max.quizspring.auth.AgentLoginRequest;
import com.max.quizspring.auth.RegisterAgentRequest;
import com.max.quizspring.config.JwtToken;
import com.max.quizspring.model.Agent;
import com.max.quizspring.model.Token;
import com.max.quizspring.repo.AgentRepo;
import com.max.quizspring.repo.JwtRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgentService {

    private final AgentRepo agentRepository;
    private final JwtRepo tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtToken jwtUtil;

    public String registerAgent(RegisterAgentRequest registerRequest) {
        Optional<Agent> agentExist = agentRepository.findByEmail(registerRequest.getEmail());
        if (agentExist.isPresent()) {
            return "Agent already exists with email id " + registerRequest.getEmail();
        }
        var agent = Agent.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .phone(registerRequest.getPhone())
                .role(Agent.Role.AGENT)
                .build();
        agentRepository.save(agent);
        return "Agent registered successfully.";
    }

    public Map<String, Object> login(AgentLoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails); // JWT generation for UserDetails

        Optional<Agent> agent = agentRepository.findByEmail(loginRequest.getEmail());
        if (agent.isEmpty()) {
            throw new RuntimeException("Agent not found");
        }

        // Save the generated token
        saveAgentToken(agent.get(), token);

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("role", agent.get().getRole().name());
        response.put("agentId", agent.get().getId()); // Add agentId to the response

        return response;
    }

    private void saveAgentToken(Agent agent, String accessToken) {
        Token token = Token.builder()
                .agent(agent)
                .token(accessToken)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public void revokeAllAgentTokens(Agent agent) {
        List<Token> validAgentTokens = tokenRepository.findAllByAgent_IdAndExpiredFalseAndRevokedFalse(agent.getId());
        if (validAgentTokens.isEmpty()) return;
        validAgentTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validAgentTokens);
    }

    public Optional<Agent> getAgentById(Long id) {
        return agentRepository.findById(id);
    }

    public Optional<Agent> authenticateAgent(String email, String password) {
        Optional<Agent> agent = agentRepository.findByEmail(email);
        if (agent.isPresent() && passwordEncoder.matches(password, agent.get().getPassword())) {
            return agent;
        }
        return Optional.empty();
    }

    public List<Agent> getAllAgents() {
        return agentRepository.findAll();
    }
    

    public boolean deleteAgentById(Long id) {
        Optional<Agent> agent = agentRepository.findById(id);
        if (agent.isPresent()) {
            agentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
