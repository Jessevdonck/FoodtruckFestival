package org.foodtruckfestival.foodtruckfestival.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NoDuplicateFoodtrucksValidator implements ConstraintValidator<NoDuplicateFoodtrucks, List<String>> {

    @Override
    public boolean isValid(List<String> foodtrucks, ConstraintValidatorContext context) {
        if (foodtrucks == null || foodtrucks.isEmpty()) {

            return false;  // of true als leeg ook mag
        }

        Set<String> set = new HashSet<>();
        boolean hasNonEmpty = false;

        for (String ft : foodtrucks) {
            if (ft == null || ft.trim().isEmpty()) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Je moet minstens één foodtruck invullen")
                        .addConstraintViolation();
                continue; // leeg negeren
            }
            hasNonEmpty = true;
            if (!set.add(ft.trim())) {
                return false; // duplicate gevonden
            }
        }
        return hasNonEmpty;  // false als alleen lege strings
    }

}
