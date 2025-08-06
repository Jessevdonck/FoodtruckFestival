package org.foodtruckfestival.foodtruckfestival.validator;

import org.foodtruckfestival.foodtruckfestival.domain.Festival;
import org.foodtruckfestival.foodtruckfestival.domain.MyUser;
import org.foodtruckfestival.foodtruckfestival.service.RegistrationService;
import org.foodtruckfestival.foodtruckfestival.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ReviewPermissionValidator {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private ReviewService reviewService;

    public boolean canUserWriteReview(MyUser user, Festival festival) {
        boolean isRegistered = registrationService.totalTicketsForFestivalByUser(user, festival) > 0;
        boolean hasWrittenReview = reviewService.hasUserWrittenReview(user, festival);
        boolean festivalPassed = festival.getDateTime().isBefore(LocalDateTime.now());

        return isRegistered && festivalPassed && !hasWrittenReview;
    }
}
