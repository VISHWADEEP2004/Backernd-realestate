package com.max.quizspring.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.max.quizspring.model.Agent;

public interface AgentRepo extends JpaRepository<Agent, Long> {

    Optional<Agent> findByEmail(String email);
}
