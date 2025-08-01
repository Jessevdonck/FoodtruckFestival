package org.foodtruckfestival.foodtruckfestival.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.foodtruckfestival.foodtruckfestival.domain.Festival;
import org.foodtruckfestival.foodtruckfestival.enums.Food;
import org.foodtruckfestival.foodtruckfestival.enums.Location;
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

private final FestivalService festivalService;

@Autowired
public FestivalController(FestivalService festivalService) {
this.festivalService = festivalService;
}

@GetMapping
public String getFestivals(Model model) {
model.addAttribute("festivals", festivalService.fetchFestivalOverview());
return "festivals";
}

@GetMapping("/{id}")
public String festivalDetails(@PathVariable Long id, Model model) {
model.addAttribute("festival", festivalService.findFestivalDTOById(id));
return "festivalDetails";
}

    @GetMapping("/add")
    public String showFestivalForm(Model model) {
        model.addAttribute("festival", new Festival());
        model.addAttribute("locaties", Location.values());
        model.addAttribute("categorieen", Food.values());
        return "festival-form";
    }

    @PostMapping("/add")
    public String processFestivalForm(
            @Valid @ModelAttribute("festival") Festival festival,
            BindingResult result, Model model) {

        model.addAttribute("locaties", Location.values());
        model.addAttribute("categorieen", Food.values());

        if (result.hasErrors()) {
            return "festival-form";
        }

        festivalService.save(festival);
        return "redirect:/festivals";
    }

}
