package org.foodtruckfestival.foodtruckfestival.validator;

import lombok.RequiredArgsConstructor;
import org.foodtruckfestival.foodtruckfestival.dto.RegistrationRequest;
import org.foodtruckfestival.foodtruckfestival.service.MyUserService;
import org.foodtruckfestival.foodtruckfestival.service.RegistrationService;
import org.foodtruckfestival.foodtruckfestival.service.FestivalService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class RegistrationValidator implements Validator {

    private final FestivalService festivalService;
    private final RegistrationService registrationService;
    private final MyUserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return RegistrationRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
    }

    public void validate(RegistrationRequest request, Long festivalId, String username, Errors errors) {
        int ticketsToBuy = request.getTicketsToBuy();
        int totalTicketsInPeriod = registrationService.countTicketsForUserInFestivalPeriod(username, festivalId);
        int ticketsAlreadyBoughtForFestival = registrationService.totalTicketsForFestivalByUser(userService.findByUsername(username), festivalService.findById(festivalId));

        // Max 30 tickets per festival
        if (ticketsAlreadyBoughtForFestival + ticketsToBuy > 30) {
            errors.rejectValue("ticketsToBuy", "tickets.limit.exceeded");
        }

        // Max 100 tickets in same festival period
        if (totalTicketsInPeriod + ticketsToBuy > 100) {
            errors.rejectValue("ticketsToBuy", "tickets.period.limit.exceeded");
        }
    }
}
