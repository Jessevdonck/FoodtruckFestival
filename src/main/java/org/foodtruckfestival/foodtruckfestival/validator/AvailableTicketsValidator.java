package org.foodtruckfestival.foodtruckfestival.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.foodtruckfestival.foodtruckfestival.domain.Festival;
import org.foodtruckfestival.foodtruckfestival.dto.RegistrationRequest;
import org.foodtruckfestival.foodtruckfestival.repository.FestivalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AvailableTicketsValidator implements ConstraintValidator<ValidAvailableTickets, RegistrationRequest> {

    @Autowired
    private FestivalRepository festivalRepository;

    @Autowired
    private MessageSource messageSource;

    @Override
    public boolean isValid(RegistrationRequest request, ConstraintValidatorContext context) {
        if (request.getFestivalId() == null || request.getTicketsToBuy() <= 0) {
            return true;
        }

        Festival festival = festivalRepository.findById(request.getFestivalId()).orElse(null);
        if (festival == null) {
            return true;
        }

        if (festival.getAvailableTickets() < request.getTicketsToBuy()) {
            context.disableDefaultConstraintViolation();
            String errorMessage = messageSource.getMessage("registration.tickets.notEnough", null, LocaleContextHolder.getLocale());
            context.buildConstraintViolationWithTemplate(errorMessage)
                    .addPropertyNode("ticketsToBuy")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
