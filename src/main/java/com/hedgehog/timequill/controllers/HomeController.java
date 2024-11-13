package com.hedgehog.timequill.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping("/")
    public String greeting(@RequestParam(name="name", required=false, defaultValue=" please login to continue using TimeQuill!") String name, Model model) {
        // Retrieve the current authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;

        // Check if the user is authenticated
        if (authentication != null && authentication.isAuthenticated()) {
            username = authentication.getName();
        }

        // Use the username if available, or the default name
        model.addAttribute("name", username != null ? username : name);

        return "home";
    }
}
