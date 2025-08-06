package org.foodtruckfestival.foodtruckfestival.controller;

import org.foodtruckfestival.foodtruckfestival.domain.Festival;
import org.foodtruckfestival.foodtruckfestival.domain.MyUser;
import org.foodtruckfestival.foodtruckfestival.domain.Review;
import org.foodtruckfestival.foodtruckfestival.service.FestivalService;
import org.foodtruckfestival.foodtruckfestival.service.MyUserService;
import org.foodtruckfestival.foodtruckfestival.service.RegistrationService;
import org.foodtruckfestival.foodtruckfestival.service.ReviewService;
import org.foodtruckfestival.foodtruckfestival.validator.ReviewPermissionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @Autowired
    ReviewPermissionValidator reviewPermissionValidator;

    @GetMapping("/{festivalId}/add")
    public String showReviewForm(@PathVariable Long festivalId, Model model, Principal principal) {
        Festival festival = festivalService.findById(festivalId);
        MyUser user = myUserService.findByUsername(principal.getName());

        if (!reviewPermissionValidator.canUserWriteReview(user, festival)) {
            return "redirect:/reviews/" + festivalId + "?error=notAllowed";
        }

        model.addAttribute("festival", festival);
        model.addAttribute("review", new Review());
        return "review-form";
    }

    @PostMapping("/{festivalId}/add")
    public String submitReview(@PathVariable Long festivalId, Review review, Principal principal) {
        Festival festival = festivalService.findById(festivalId);
        MyUser user = myUserService.findByUsername(principal.getName());

        review.setUser(user);
        review.setFestival(festival);
        review.setPostedAt(LocalDateTime.now());
        reviewService.save(review);

        return "redirect:/reviews/" + festivalId;
    }



    @GetMapping("/{festivalId}")
    public String viewReviews(@PathVariable Long festivalId, Model model, Principal principal) {
        Festival festival = festivalService.findById(festivalId);

        List<Review> reviews = reviewService.getReviewsForFestival(festival);
        Double avgScore = reviewService.getAverageScoreForFestival(festival);
        MyUser user = myUserService.findByUsername(principal.getName());
        boolean canWriteReview = reviewPermissionValidator.canUserWriteReview(user, festival);

        model.addAttribute("festival", festival);
        model.addAttribute("reviews", reviews);
        model.addAttribute("averageScore", avgScore);
        model.addAttribute("canWriteReview", canWriteReview);

        return "reviews";
    }
}

