package org.foodtruckfestival.foodtruckfestival.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.foodtruckfestival.foodtruckfestival.domain.Festival;
import org.foodtruckfestival.foodtruckfestival.dto.FestivalDTO;
import org.foodtruckfestival.foodtruckfestival.dto.RegistrationRequest;
import org.foodtruckfestival.foodtruckfestival.service.FestivalService;
import org.foodtruckfestival.foodtruckfestival.service.RegistrationService;
import org.foodtruckfestival.foodtruckfestival.validator.FestivalCategoryConflictOnSameDayValidator;
import org.foodtruckfestival.foodtruckfestival.validator.RegistrationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@Slf4j
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    RegistrationService registrationService;
    @Autowired
    FestivalService festivalService;

    @Autowired
    RegistrationValidator registrationValidator;

    @GetMapping("/{festivalId}")
    public String showRegistrationForm(@PathVariable Long festivalId, Model model) {
        FestivalDTO festival = festivalService.findFestivalDTOById(festivalId);
        model.addAttribute("festival", festival);
        model.addAttribute("registrationRequest", new RegistrationRequest());

        return "festivalDetails";
    }

    @PostMapping("/{festivalId}")
    public String registerForFestival(
            @PathVariable Long festivalId,
            @ModelAttribute @Valid RegistrationRequest request,
            BindingResult result,
            Principal principal,
            Model model) {

        FestivalDTO festival = festivalService.findFestivalDTOById(festivalId);

        registrationValidator.validate(request, festivalId, principal.getName(), result);

        if (result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            model.addAttribute("festival", festival);
            return "festivalDetails";
        }

        String message = registrationService.registerForFestival(festivalId, principal.getName(), request.getTicketsToBuy());

        model.addAttribute("message", message);
        model.addAttribute("ticketsBought", request.getTicketsToBuy());
        return "registrationSuccess";
    }
}
