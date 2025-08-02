    package org.foodtruckfestival.foodtruckfestival.validator;

    import jakarta.validation.Constraint;
    import jakarta.validation.Payload;

    import java.lang.annotation.*;

    @Documented
    @Constraint(validatedBy = NoDuplicateFoodtrucksValidator.class)
    @Target({ ElementType.FIELD, ElementType.METHOD })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface NoDuplicateFoodtrucks {
        String message() default "Geen dubbele standhouders toegestaan";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }
