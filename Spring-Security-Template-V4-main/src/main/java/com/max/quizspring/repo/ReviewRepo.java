package com.max.quizspring.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.max.quizspring.model.Review;

public interface ReviewRepo extends JpaRepository<Review, Long> {
}

