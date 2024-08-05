package com.max.quizspring.service;


import com.max.quizspring.auth.ReviewRequest;
import com.max.quizspring.model.Property;
import com.max.quizspring.model.Review;
import com.max.quizspring.repo.PropertyRepos;
import com.max.quizspring.repo.ReviewRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ReviewService {

    @Autowired
    private ReviewRepo reviewRepository;

    @Autowired
    private PropertyRepos propertyRepository;

    public Review createReview(ReviewRequest reviewDto) {
        Review review = new Review();
        review.setComment(reviewDto.getComment());
        review.setRating(reviewDto.getRating());
        
        Property property = propertyRepository.findById(reviewDto.getPropertyId())
                                             .orElseThrow(() -> new RuntimeException("Property not found"));
        review.setProperty(property);
        
        return reviewRepository.save(review);
    }

    public Review updateReview(Long id, ReviewRequest reviewDto) {
        if (reviewRepository.existsById(id)) {
            Review review = reviewRepository.findById(id).get();
            review.setComment(reviewDto.getComment());
            review.setRating(reviewDto.getRating());

            Property property = propertyRepository.findById(reviewDto.getPropertyId())
                                                 .orElseThrow(() -> new RuntimeException("Property not found"));
            review.setProperty(property);

            return reviewRepository.save(review);
        } else {
            return null;
        }
    }
}
