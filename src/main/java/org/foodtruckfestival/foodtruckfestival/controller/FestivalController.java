package org.foodtruckfestival.foodtruckfestival.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
}
