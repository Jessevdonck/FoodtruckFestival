package org.foodtruckfestival.foodtruckfestival.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NoDuplicateFoodtrucksValidator implements ConstraintValidator<NoDuplicateFoodtrucks, List<String>> {

    @Override
    public boolean isValid(List<String> foodtrucks, ConstraintValidatorContext context) {
        Set<String> set = new HashSet<>();
        for (String ft : foodtrucks) {
            if (!set.add(ft)) {
                return false;
            }
        }
        return true;
    }
}
