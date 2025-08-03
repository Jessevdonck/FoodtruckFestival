package org.foodtruckfestival.foodtruckfestival.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.foodtruckfestival.foodtruckfestival.domain.Festival;
import org.foodtruckfestival.foodtruckfestival.dto.RegistrationRequest;
import org.foodtruckfestival.foodtruckfestival.repository.FestivalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AvailableTicketsValidator implements ConstraintValidator<ValidAvailableTickets, RegistrationRequest> {

    @Autowired
    private FestivalRepository festivalRepository;

    @Override
    public boolean isValid(RegistrationRequest request, ConstraintValidatorContext context) {
        if (request.getFestivalId() == null || request.getTicketsToBuy() <= 0) {
            return true;
        }

        Festival festival = festivalRepository.findById(request.getFestivalId()).orElse(null);
        if (festival == null) {
            return true; // validatie van bestaan gebeurt elders (of kan je hier nog toevoegen)
        }

        if (festival.getAvailableTickets() < request.getTicketsToBuy()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Niet genoeg tickets beschikbaar.")
                    .addPropertyNode("ticketsToBuy")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
