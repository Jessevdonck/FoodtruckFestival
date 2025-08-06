package org.foodtruckfestival.foodtruckfestival.controller;

import org.foodtruckfestival.foodtruckfestival.domain.Festival;
import org.foodtruckfestival.foodtruckfestival.domain.MyUser;
import org.foodtruckfestival.foodtruckfestival.domain.Review;
import org.foodtruckfestival.foodtruckfestival.service.FestivalService;
import org.foodtruckfestival.foodtruckfestival.service.MyUserService;
import org.foodtruckfestival.foodtruckfestival.service.RegistrationService;
import org.foodtruckfestival.foodtruckfestival.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private FestivalService festivalService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private MyUserService myUserService;

    @GetMapping("/{festivalId}")
    public String viewReviews(@PathVariable Long festivalId, Model model, Principal principal) {
        Festival festival = festivalService.findById(festivalId);

        List<Review> reviews = reviewService.getReviewsForFestival(festival);
        Double avgScore = reviewService.getAverageScoreForFestival(festival);

        boolean canWriteReview = false;
        if (principal != null) {
            MyUser user = myUserService.findByUsername(principal.getName());
            boolean isRegistered = registrationService.totalTicketsForFestivalByUser(user, festival) > 0;
            boolean hasWrittenReview = reviewService.hasUserWrittenReview(user, festival);
            boolean festivalPassed = festival.getDateTime().isBefore(LocalDateTime.now());

            canWriteReview = isRegistered && festivalPassed && !hasWrittenReview;
        }

        model.addAttribute("festival", festival);
        model.addAttribute("reviews", reviews);
        model.addAttribute("averageScore", avgScore);
        model.addAttribute("canWriteReview", canWriteReview);

        return "reviews";
    }
}

