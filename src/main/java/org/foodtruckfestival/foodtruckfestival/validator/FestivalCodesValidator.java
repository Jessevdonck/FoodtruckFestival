package org.foodtruckfestival.foodtruckfestival.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.foodtruckfestival.foodtruckfestival.domain.Festival;

public class FestivalCodesValidator implements ConstraintValidator<ValidFestivalCodes, Festival> {

    @Override
    public boolean isValid(Festival festival, ConstraintValidatorContext context) {
        if (festival == null) return true;

        Integer code1 = festival.getFestivalCode1();
        Integer code2 = festival.getFestivalCode2();

        boolean valid = true;
        context.disableDefaultConstraintViolation();

        if (code1 != null) {
            if (code1 <= 0 || code1 % 2 != 0) {
                context.buildConstraintViolationWithTemplate("Festivalcode1 moet een strikt even positief getal zijn.")
                        .addPropertyNode("festivalCode1")
                        .addConstraintViolation();
                valid = false;
            }
        }

        if (code2 != null) {
            if (code2 % 3 != 0) {
                context.buildConstraintViolationWithTemplate("Festivalcode2 moet deelbaar zijn door 3.")
                        .addPropertyNode("festivalCode2")
                        .addConstraintViolation();
                valid = false;
            }
        }

        if (code1 != null && code2 != null) {
            if (Math.abs(code1 - code2) >= 300) {
                context.buildConstraintViolationWithTemplate("Het verschil tussen festivalcode1 en festivalcode2 moet kleiner zijn dan 300.")
                        .addPropertyNode("festivalCode2")
                        .addConstraintViolation();
                valid = false;
            }
        }

        return valid;
    }
}
