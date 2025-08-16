package org.foodtruckfestival.foodtruckfestival.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.foodtruckfestival.foodtruckfestival.domain.Festival;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;

public class FestivalCodesValidator implements ConstraintValidator<ValidFestivalCodes, Festival> {

    @Autowired
    private MessageSource  messageSource;

    @Override
    public boolean isValid(Festival festival, ConstraintValidatorContext context) {
        if (festival == null) return true;

        Integer code1 = festival.getFestivalCode1();
        Integer code2 = festival.getFestivalCode2();

        boolean valid = true;
        context.disableDefaultConstraintViolation();

        if (code1 != null) {
            if (code1 <= 0 || code1 % 2 != 0) {
                String errorMessage = messageSource.getMessage("festival.code1.invalid", null, Locale.getDefault());
                context.buildConstraintViolationWithTemplate(errorMessage)
                        .addPropertyNode("festivalCode1")
                        .addConstraintViolation();
                valid = false;
            }
        }

        if (code2 != null) {
            if (code2 % 3 != 0) {
                String errorMessage = messageSource.getMessage("festival.code2.invalid", null, Locale.getDefault());
                context.buildConstraintViolationWithTemplate(errorMessage)
                        .addPropertyNode("festivalCode2")
                        .addConstraintViolation();
                valid = false;
            }
        }

        if (code1 != null && code2 != null) {
            if (Math.abs(code1 - code2) >= 300) {
                String errorMessage = messageSource.getMessage("festival.codes.diff.invalid", new Object[]{300}, Locale.getDefault());
                context.buildConstraintViolationWithTemplate(errorMessage)
                        .addPropertyNode("festivalCode2")
                        .addConstraintViolation();
                valid = false;
            }
        }

        return valid;
    }
}
