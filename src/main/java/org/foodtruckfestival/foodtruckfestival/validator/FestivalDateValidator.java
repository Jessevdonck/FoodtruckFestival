package org.foodtruckfestival.foodtruckfestival.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.foodtruckfestival.foodtruckfestival.domain.Festival;
import org.foodtruckfestival.foodtruckfestival.validator.ValidFestivalDate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FestivalDateValidator implements ConstraintValidator<ValidFestivalDate, Festival> {

    private LocalDate startDate;
    private LocalDate endDate;

    @Override
    public void initialize(ValidFestivalDate constraintAnnotation) {
        this.startDate = LocalDate.parse(constraintAnnotation.start());
        this.endDate = LocalDate.parse(constraintAnnotation.end());
    }

    @Override
    public boolean isValid(Festival festival, ConstraintValidatorContext context) {
        if (festival == null || festival.getDateTime() == null) {
            return false;
        }

        LocalDate date = festival.getDateTime().toLocalDate();

        if (date.isBefore(startDate) || date.isAfter(endDate)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "De datum moet tussen " + startDate + " en " + endDate + " liggen."
            ).addPropertyNode("dateTime").addConstraintViolation();
            return false;
        }

        return true;
    }
}
