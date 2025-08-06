package org.foodtruckfestival.foodtruckfestival.service;

import org.foodtruckfestival.foodtruckfestival.domain.Festival;
import org.foodtruckfestival.foodtruckfestival.domain.MyUser;
import org.foodtruckfestival.foodtruckfestival.domain.Review;
import org.foodtruckfestival.foodtruckfestival.exceptions.ReviewNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.foodtruckfestival.foodtruckfestival.repository.ReviewRepository;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService
    {
        @Autowired
        private ReviewRepository reviewRepository;

        @Override
        public Review save(Review review) {
            return reviewRepository.save(review);
        }

        @Override
        public Review findById(Long id) {
            return reviewRepository.findById(id).orElse(null);
        }

        @Override
        public List<Review> getReviewsForFestival(Festival festival) {
            return reviewRepository.findByFestivalOrderByPostedAtDesc(festival);
        }

        @Override
        public boolean hasUserWrittenReview(MyUser user, Festival festival) {
            return reviewRepository.existsByUserAndFestival(user, festival);
        }

        @Override
        public Double getAverageScoreForFestival(Festival festival) {
            Double avg = reviewRepository.findAverageScoreByFestival(festival);
            return avg != null ? avg : 0.0  ;
        }
}
