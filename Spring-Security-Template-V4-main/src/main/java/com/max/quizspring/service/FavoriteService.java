package com.max.quizspring.service;


import com.max.quizspring.auth.FavoriteRequest;
import com.max.quizspring.model.Favorite;
import com.max.quizspring.model.Property;
import com.max.quizspring.model.User;
import com.max.quizspring.repo.FavoriteRepo;
import com.max.quizspring.repo.PropertyRepos;
import com.max.quizspring.repo.UserRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class FavoriteService {

    private final FavoriteRepo favoriteRepo;
    private final UserRepo userRepo;
    private final PropertyRepos propertyRepo;

    public FavoriteService(FavoriteRepo favoriteRepo, UserRepo userRepo, PropertyRepos propertyRepo) {
        this.favoriteRepo = favoriteRepo;
        this.userRepo = userRepo;
        this.propertyRepo = propertyRepo;
    }

    public Favorite addFavorite(FavoriteRequest request) {
        Optional<User> userOptional = userRepo.findById(request.getUserId());
        Optional<Property> propertyOptional = propertyRepo.findById(request.getPropertyId());

        if (userOptional.isPresent() && propertyOptional.isPresent()) {
            Favorite favorite = new Favorite();
            favorite.setUser(userOptional.get());
            favorite.setProperty(propertyOptional.get());
            favorite.setAddedAt(LocalDateTime.now()); // Set the current time
            return favoriteRepo.save(favorite);
        } else {
            throw new RuntimeException("User or Property not found");
        }
    }
}
