package org.foodtruckfestival.foodtruckfestival.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PriceValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPrice {
    String message() default "Prijs moet minstens €10,50 en strikt kleiner dan €40,00 zijn";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
