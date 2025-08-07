package org.foodtruckfestival.foodtruckfestival.controller;

import org.foodtruckfestival.foodtruckfestival.exceptions.FestivalConflictException;
import org.foodtruckfestival.foodtruckfestival.exceptions.FestivalNotFoundException;
import org.foodtruckfestival.foodtruckfestival.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleRuntimeException(RuntimeException ex, Model model, WebRequest request) {
        String requestUri = request.getDescription(false);
        if (requestUri.contains("/festivals")) {
            model.addAttribute("festivals", Collections.emptyList());
            model.addAttribute("errorMessage", "An error occurred while fetching data.");
            return new ModelAndView("festivals");
        } else {
            ModelAndView mav = new ModelAndView("error");
            mav.addObject("message", "Internal Server Error");
            return mav;
        }
    }

    @ExceptionHandler(IllegalStateException.class)
    public String handleIllegalStateException(IllegalStateException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "error";
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFound(UserNotFoundException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:/error";
    }

    @ExceptionHandler(FestivalNotFoundException.class)
    public String handleFestivalNotFound(FestivalNotFoundException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:/error";
    }
}