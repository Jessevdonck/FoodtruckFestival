package org.foodtruckfestival.foodtruckfestival.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.foodtruckfestival.foodtruckfestival.domain.Festival;
import org.foodtruckfestival.foodtruckfestival.utils.FestivalConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.time.LocalDate;
import java.util.Locale;

public class FestivalDateValidator implements ConstraintValidator<ValidFestivalDate, Festival> {

    @Autowired
    private MessageSource messageSource;

    @Override
    public boolean isValid(Festival festival, ConstraintValidatorContext context) {
        if (festival == null || festival.getDateTime() == null) {
            return false;
        }

        LocalDate date = festival.getDateTime().toLocalDate();

        if (date.isBefore(FestivalConstants.START_DATE) || date.isAfter(FestivalConstants.END_DATE)) {
            context.disableDefaultConstraintViolation();

            String errorMessage = messageSource.getMessage(
                    "festival.date.invalid",
                    new Object[]{FestivalConstants.START_DATE, FestivalConstants.END_DATE},
                    Locale.getDefault()
            );

            context.buildConstraintViolationWithTemplate(errorMessage)
                    .addPropertyNode("dateTime")
                    .addConstraintViolation();

            return false;
        }

        return true;
    }
}
