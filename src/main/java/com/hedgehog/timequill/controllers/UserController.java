package com.hedgehog.timequill.controllers;

import com.hedgehog.timequill.entities.UserEntity;
import com.hedgehog.timequill.repo.UserRepository;
import com.hedgehog.timequill.services.DBUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DBUserService userDetailsManager;

    // get login, returns login page
    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpSession session) {
        session.setAttribute(
                "error", "SPRING_SECURITY_LAST_EXCEPTION");
        return "/account/userLogin";
    }

    // logs user out, performs clean up, and redirects to homepage
    @GetMapping("/account/logout")
    public String userLogout(HttpSecurity http) throws Exception {
        HeaderWriterLogoutHandler clearSiteData = new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter());
        http.logout((logout) -> logout.addLogoutHandler(clearSiteData));
        return "redirect:/";
    }

    // gets current user and their manager
    @GetMapping("/account/view")
    public String view(Model model) {
        UserEntity user = userRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        UserEntity manager = null;
        try {
            manager = userRepo.findById(user.getManagerId().getId()).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("manager", manager);
        model.addAttribute("user", user);

        return "/account/userView";
    }

    // post new user, creates a new user account
    @PostMapping("/account/create")
    public String create(@RequestParam String username, @RequestParam String password,
            @RequestParam String title, @RequestParam String supervisor) {

        boolean isManager = false;
        if (title.equals("Manager")) {
            isManager = true;
        }

        UserEntity u = new UserEntity();
        u.setUsername(username);
        u.setPassword(passwordEncoder.encode(password));
        u.setManager(isManager);
        u.setManagerId(userRepo.findByUsername(supervisor));
        u.setLocked(false);
        userRepo.save(u);
        return "redirect:/account/admin";
    }

}
