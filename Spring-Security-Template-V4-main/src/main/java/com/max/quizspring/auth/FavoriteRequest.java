package com.max.quizspring.auth;

import jakarta.validation.constraints.NotNull;

public class FavoriteRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long propertyId;



    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }
}
