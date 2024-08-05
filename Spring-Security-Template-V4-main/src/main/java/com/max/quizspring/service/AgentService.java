package com.max.quizspring.service;


import org.springframework.stereotype.Service;

import com.max.quizspring.model.Agent;
import com.max.quizspring.repo.AgentRepo;

import java.util.List;
import java.util.Optional;

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

    public List<Agent> getAllAgents() {
        return agentRepository.findAll();
    }

    public Agent updateAgent(Long id, Agent updatedAgent) {
        if (agentRepository.existsById(id)) {
            updatedAgent.setId(id);
            return agentRepository.save(updatedAgent);
        }
        return null;
    }

    public boolean deleteAgent(Long id) {
        if (agentRepository.existsById(id)) {
            agentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
