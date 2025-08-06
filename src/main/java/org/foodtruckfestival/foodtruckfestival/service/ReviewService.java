package org.foodtruckfestival.foodtruckfestival.service;

import org.foodtruckfestival.foodtruckfestival.domain.Festival;
import org.foodtruckfestival.foodtruckfestival.domain.MyUser;
import org.foodtruckfestival.foodtruckfestival.domain.Review;

import java.util.List;

public interface ReviewService {
    Review save(Review review);
    Review findById(Long id);
    List<Review> getReviewsForFestival(Festival festival);
    boolean hasUserWrittenReview(MyUser user, Festival festival);
    Double getAverageScoreForFestival(Festival festival);
}
