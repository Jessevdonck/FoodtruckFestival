package org.foodtruckfestival.foodtruckfestival.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FestivalCodesValidator.class)
@Documented
public @interface ValidFestivalCodes {
    String message() default "{festival.code.default}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
