package com.max.quizspring.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.max.quizspring.model.Agent;
import com.max.quizspring.repo.AgentRepo;

@Service
public class AgentService {

    private final AgentRepo agentRepository;

    public AgentService(AgentRepo agentRepository) {
        this.agentRepository = agentRepository;
    }

    public Agent addAgent(Agent agent) {
        return agentRepository.save(agent);
    }

    public Optional<Agent> getAgentById(Long id) {
        return agentRepository.findById(id);
    }
}
