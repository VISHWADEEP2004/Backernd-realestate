package com.max.quizspring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.max.quizspring.model.Review;
import com.max.quizspring.repo.ReviewRepo;

import jakarta.validation.Valid;

@Service
public class ReviewService {

    private final ReviewRepo reviewRepository;

    public ReviewService(ReviewRepo reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review addReview(Review review) {
        return reviewRepository.save(review);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review getReviewById(Long id) {
        Optional<Review> review = reviewRepository.findById(id);
        return review.orElse(null);
    }

    public Review updateReview(Long id, @Valid Review review) {
        if (reviewRepository.existsById(id)) {
            review.setId(id); // Assuming setId method is present in Review model
            return reviewRepository.save(review);
        }
        return null;
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}
