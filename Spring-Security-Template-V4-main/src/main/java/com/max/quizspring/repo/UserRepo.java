package com.max.quizspring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.max.quizspring.model.User;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email); // Keep this if you still need to search by email
}
