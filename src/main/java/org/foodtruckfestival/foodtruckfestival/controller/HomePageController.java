package org.foodtruckfestival.foodtruckfestival.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {

    @GetMapping("/")
    public String redirectToDashboard() {
        return "redirect:/festivals";
    }
}