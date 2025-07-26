package org.foodtruckfestival.foodtruckfestival.service;

import org.foodtruckfestival.foodtruckfestival.domain.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.foodtruckfestival.foodtruckfestival.repository.ReviewRepository;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService
    {

private final ReviewRepository reviewRepository;

@Autowired
public ReviewServiceImpl(ReviewRepository reviewRepository) {
this.reviewRepository = reviewRepository;
}

@Override
public Review findById(Long id) {
return reviewRepository.findById(id).orElse(null);
}

@Override
public List<Review> findAll() {
return reviewRepository.findAll();
}

@Override
public Review save(Review review) {
return reviewRepository.save(review);
}

@Override
public void deleteById(Long id) {
reviewRepository.deleteById(id);
}
}
