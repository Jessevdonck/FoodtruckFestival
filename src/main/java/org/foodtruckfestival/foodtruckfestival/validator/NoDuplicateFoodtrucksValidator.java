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
            return true; // Laat andere validators NotEmpty en @NotBlank hun werk doen
        }

        Set<String> set = new HashSet<>();
        for (String ft : foodtrucks) {
            if (ft == null || ft.trim().isEmpty()) {
                continue; // Negeer lege waarden
            }
            if (!set.add(ft.trim())) {
                return false; // Duplicate gevonden
            }
        }
        return true;
    }

}
