package org.foodtruckfestival.foodtruckfestival.exceptions;

public class ReviewNotFoundException extends RuntimeException {

    public ReviewNotFoundException(Long id) {
        super("Review not found with ID: " + id);
    }
}
