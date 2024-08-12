package com.max.quizspring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.max.quizspring.model.Property;
import java.util.List;

public interface PropertyRepos extends JpaRepository<Property, Long> {
    List<Property> findByLocation(String location);
}
