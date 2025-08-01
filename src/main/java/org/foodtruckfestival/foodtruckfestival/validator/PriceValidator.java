package org.foodtruckfestival.foodtruckfestival.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class PriceValidator implements ConstraintValidator<ValidPrice, BigDecimal> {

    private static final BigDecimal MIN = new BigDecimal("10.50");
    private static final BigDecimal MAX = new BigDecimal("40.00");

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        if (value == null) return false;
        return value.compareTo(MIN) >= 0 && value.compareTo(MAX) < 0;
    }
}
