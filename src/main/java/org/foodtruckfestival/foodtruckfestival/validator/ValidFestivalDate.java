package org.foodtruckfestival.foodtruckfestival.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.foodtruckfestival.foodtruckfestival.validator.FestivalDateValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FestivalDateValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidFestivalDate {
    String message() default "{festival.date.default}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
