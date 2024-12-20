package com.hedgehog.timequill.controllers;

import com.hedgehog.timequill.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    UserRepository userRepo;

    // get current user authentication, if authenticated set user attributes
    @GetMapping("/")
    public String greeting(
            @RequestParam(name = "name", required = false, defaultValue = " please login to continue using TimeQuill!") String name,
            Model model) {
        // Retrieve the current authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        String status = "Login";

        // Check if the user is authenticated
        if (authentication != null && authentication.isAuthenticated()
                && !authentication.getName().equals("anonymousUser"))
            username = authentication.getName();

        // Use the username if available, or the default name
        model.addAttribute("name", username != null ? username : name);
        model.addAttribute("manager", userRepo.findByUsername(username).getManager());
        model.addAttribute("status", username != null ? "Account" : status);

        return "home";
    }
}
