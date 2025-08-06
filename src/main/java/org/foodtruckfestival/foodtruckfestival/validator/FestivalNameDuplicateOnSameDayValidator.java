package org.foodtruckfestival.foodtruckfestival.validator;

import org.foodtruckfestival.foodtruckfestival.domain.Festival;
import org.foodtruckfestival.foodtruckfestival.repository.FestivalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.util.List;

@Component
public class FestivalNameDuplicateOnSameDayValidator implements Validator {

    @Autowired
    private FestivalRepository festivalRepository;

    @Autowired
    MessageSource messageSource;

    @Override
    public boolean supports(Class<?> clazz) {
        return Festival.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Festival festival = (Festival) target;

        if (festival.getName() == null || festival.getDateTime() == null) {
            return;
        }

        LocalDate targetDate = festival.getDateTime().toLocalDate();

        List<Festival> existingFestivals = festivalRepository.findByName(festival.getName());

        for (Festival existingFestival : existingFestivals) {
            if (festival.getId() != null && festival.getId().equals(existingFestival.getId())) {
                continue;
            }

            if (existingFestival.getDateTime().toLocalDate().isEqual(targetDate)) {
                errors.rejectValue("name", "festival.name.duplicateOnDate");
                break;
            }
        }
    }
}
