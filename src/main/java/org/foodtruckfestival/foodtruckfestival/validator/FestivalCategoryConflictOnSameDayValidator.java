package org.foodtruckfestival.foodtruckfestival.validator;

import org.foodtruckfestival.foodtruckfestival.domain.Festival;
import org.foodtruckfestival.foodtruckfestival.repository.FestivalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.util.List;

@Component
public class FestivalCategoryConflictOnSameDayValidator implements Validator {

    @Autowired
    private FestivalRepository festivalRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return Festival.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Festival festival = (Festival) target;

        if (festival.getCategorie() == null || festival.getDateTime() == null) {
            return; // Skip if incomplete data
        }

        LocalDate targetDate = festival.getDateTime().toLocalDate();

        List<Festival> existingFestivals = festivalRepository.findByCategorie(festival.getCategorie());

        for (Festival existingFestival : existingFestivals) {
            if (existingFestival.getId().equals(festival.getId())) {
                continue; // Skip self
            }

            if (existingFestival.getDateTime().toLocalDate().isEqual(targetDate)) {
                errors.rejectValue("categorie", "festival.categorie.conflictOnDate", "Er vindt al een festival met deze categorie plaats op dezelfde dag.");
                break;
            }
        }
    }
}
