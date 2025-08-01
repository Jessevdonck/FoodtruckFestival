package org.foodtruckfestival.foodtruckfestival.controller;

import org.foodtruckfestival.foodtruckfestival.enums.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute
    public void addAttributes(Model model, Principal principal, Authentication authentication) {
        if (principal != null) {
            model.addAttribute("username", principal.getName());

            // Get the user role
            String userRole = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst()
                    .orElse(String.valueOf(Role.USER)); // default to "USER" if no role is found
            String simpleUserRole = userRole.substring(5).toLowerCase();
            model.addAttribute("userRole", simpleUserRole);
        }
    }
}