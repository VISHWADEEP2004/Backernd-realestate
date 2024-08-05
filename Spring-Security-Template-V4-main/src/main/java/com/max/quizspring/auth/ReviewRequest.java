package com.max.quizspring.auth;

public class ReviewRequest {
    public class ReviewDto {
        private Long id;
        private String comment;
        private Integer rating;
        private Long propertyId;
        private Long userId;
        public Long getUserId() {
            return userId;
        }
        public void setUserId(Long userId) {
            this.userId = userId;
        }
        public Long getId() {
            return id;
        }
        public void setId(Long id) {
            this.id = id;
        }
        public String getComment() {
            return comment;
        }
        public void setComment(String comment) {
            this.comment = comment;
        }
        public Integer getRating() {
            return rating;
        }
        public void setRating(Integer rating) {
            this.rating = rating;
        }
        public Long getPropertyId() {
            return propertyId;
        }
        public void setPropertyId(Long propertyId) {
            this.propertyId = propertyId;
        }

    }

    public String getComment() {
        throw new UnsupportedOperationException("Unimplemented method 'getComment'");
    }

    public Integer getRating() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRating'");
    }

    public Long getPropertyId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPropertyId'");
    }
    
}
