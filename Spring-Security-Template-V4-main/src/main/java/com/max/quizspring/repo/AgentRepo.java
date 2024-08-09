package com.max.quizspring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.max.quizspring.model.Agent;

public interface AgentRepo extends JpaRepository<Agent, Long> {
}
