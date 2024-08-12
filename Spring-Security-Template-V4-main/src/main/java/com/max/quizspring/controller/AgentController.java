package com.max.quizspring.controller;

import com.max.quizspring.auth.AgentLoginRequest;
import com.max.quizspring.auth.RegisterAgentRequest;
import com.max.quizspring.config.JwtToken;
import com.max.quizspring.model.Agent;
import com.max.quizspring.service.AgentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/agents")
public class AgentController {

    private final AgentService agentService;
    private final JwtToken jwtUtil;

    public AgentController(AgentService agentService, JwtToken jwtUtil) {
        this.agentService = agentService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerAgent(@RequestBody RegisterAgentRequest registerRequest) {
        try {
            String response = agentService.registerAgent(registerRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred during registration", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginAgent(@RequestBody AgentLoginRequest loginRequest) {
        try {
            Map<String, Object> response = agentService.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred during login: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Agent>> getAllAgents() {
        List<Agent> agents = agentService.getAllAgents();
        return new ResponseEntity<>(agents, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agent> getAgentById(@PathVariable Long id) {
        Optional<Agent> optionalAgent = agentService.getAgentById(id);
        if (optionalAgent.isPresent()) {
            return new ResponseEntity<>(optionalAgent.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAgent(@PathVariable Long id) {
        try {
            boolean isDeleted = agentService.deleteAgentById(id);
            if (isDeleted) {
                return new ResponseEntity<>("Agent deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Agent not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while deleting the agent", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
