package org.foodtruckfestival.foodtruckfestival.controller;

import org.foodtruckfestival.foodtruckfestival.domain.Festival;
import org.foodtruckfestival.foodtruckfestival.enums.Food;
import org.foodtruckfestival.foodtruckfestival.service.FestivalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
public class FestivalRestController {

    @Autowired
    FestivalService festivalService;

    // 1. Ophalen van festivals van een categorie
    @GetMapping("/festivals")
    public List<Festival> getFestivalsByCategory(@RequestParam Food category) {
        return festivalService.getFestivalsByCategory(category);
    }

    // 2. Ophalen van aantal beschikbare tickets van een festival
    @GetMapping("/festival/{id}/tickets")
    public int getAvailableTickets(@PathVariable Long id) {
        return festivalService.getAvailableTickets(id);
    }
}

