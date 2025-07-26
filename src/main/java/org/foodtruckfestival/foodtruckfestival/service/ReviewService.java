package org.foodtruckfestival.foodtruckfestival.service;

import org.foodtruckfestival.foodtruckfestival.domain.Review;

import java.util.List;

public interface ReviewService {
Review findById(Long id);
List<Review> findAll();
Review save(Review review);
void deleteById(Long id);
}
