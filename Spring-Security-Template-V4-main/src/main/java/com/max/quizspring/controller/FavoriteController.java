package com.max.quizspring.controller;

import com.max.quizspring.auth.FavoriteRequest;
import com.max.quizspring.model.Favorite;
import com.max.quizspring.service.FavoriteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping
    public ResponseEntity<Favorite> addFavorite(@RequestBody FavoriteRequest favoriteRequest) {
        Favorite savedFavorite = favoriteService.addFavorite(favoriteRequest);
        return new ResponseEntity<>(savedFavorite, HttpStatus.CREATED);
    }

}
