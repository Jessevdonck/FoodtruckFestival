package org.foodtruckfestival.foodtruckfestival.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.foodtruckfestival.foodtruckfestival.domain.Festival;
import org.foodtruckfestival.foodtruckfestival.dto.RegistrationRequest;
import org.foodtruckfestival.foodtruckfestival.enums.Food;
import org.foodtruckfestival.foodtruckfestival.enums.Location;
import org.foodtruckfestival.foodtruckfestival.validator.FestivalCategoryConflictOnSameDayValidator;
import org.foodtruckfestival.foodtruckfestival.validator.FestivalNameDuplicateOnSameDayValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.foodtruckfestival.foodtruckfestival.service.FestivalService;

@Slf4j
@Controller
@RequestMapping("/festivals")
public class FestivalController {

@Autowired
FestivalCategoryConflictOnSameDayValidator categoryConflictOnSameDayValidator;

@Autowired
FestivalNameDuplicateOnSameDayValidator nameDuplicateOnSameDayValidator;

@Autowired FestivalService festivalService;

@GetMapping
public String getFestivals(Model model) {
    model.addAttribute("festivals", festivalService.fetchFestivalOverview());
    return "festivals";
}

@GetMapping("/{id}")
public String festivalDetails(@PathVariable Long id, Model model) {
    model.addAttribute("festival", festivalService.findFestivalDTOById(id));
    model.addAttribute("registrationRequest", new RegistrationRequest());
    return "festivalDetails";
}

    @GetMapping("/add")
    public String showFestivalForm(Model model) {
        model.addAttribute("festival", new Festival());
        model.addAttribute("locaties", Location.values());
        model.addAttribute("categorieen", Food.values());
        return "festival-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditFestivalForm(@PathVariable Long id, Model model) {
        Festival festival = festivalService.findById(id);
        model.addAttribute("festival", festival);
        model.addAttribute("locaties", Location.values());
        model.addAttribute("categorieen", Food.values());
        System.out.println(festival.getDateTime());
        return "festival-form";
    }

    @PostMapping("/add")
    public String processFestivalForm(
            @Valid @ModelAttribute("festival") Festival festival,
            BindingResult result, Model model) {

        categoryConflictOnSameDayValidator.validate(festival, result);
        nameDuplicateOnSameDayValidator.validate(festival, result);

        if(result.hasErrors()){
            model.addAttribute("festival", festival);
            model.addAttribute("locaties", Location.values());
            model.addAttribute("categorieen", Food.values());
            return "festival-form";
        }

        model.addAttribute("locaties", Location.values());
        model.addAttribute("categorieen", Food.values());

        festivalService.save(festival);
        return "redirect:/festivals";
    }

        @PostMapping("/edit/{id}")
        public String updateFestival(@PathVariable Long id, @Valid Festival festival, BindingResult result, Model model) {
            categoryConflictOnSameDayValidator.validate(festival, result);
            nameDuplicateOnSameDayValidator.validate(festival, result);

            if (result.hasErrors()) {
                System.out.println("Validation errors: " + result.getAllErrors());
                model.addAttribute("locaties", Location.values());
                model.addAttribute("categorieen", Food.values());
                return "festival-form";
            }

            festivalService.updateFestival(id, festival);
            return "redirect:/festivals";
        }
}
