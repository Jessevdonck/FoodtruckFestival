package org.foodtruckfestival.foodtruckfestival.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AvailableTicketsValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAvailableTickets {
    String message() default "Niet genoeg beschikbare tickets voor deze aanvraag.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
