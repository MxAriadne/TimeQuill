package com.hedgehog.timequill.controllers;

import com.hedgehog.timequill.entities.UserEntity;
import com.hedgehog.timequill.repo.UserRepository;
import com.hedgehog.timequill.services.DBUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private DBUserService userDetailsManager;

    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpSession session) {
        session.setAttribute(
                "error", "SPRING_SECURITY_LAST_EXCEPTION"
        );
        return "/account/userLogin";
    }

    @GetMapping("/account/logout")
    public String userLogout(HttpSecurity http) throws Exception {
        HeaderWriterLogoutHandler clearSiteData = new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter());
        http.logout((logout) -> logout.addLogoutHandler(clearSiteData));
        return "redirect:/";
    }

    @GetMapping("/account/view")
    public String view() {
        return "/account/userView";
    }

    @PostMapping("/account/create")
    public @ResponseBody String create(@RequestParam String username, @RequestParam String password) {
        UserEntity u = new UserEntity();
        u.setUsername(username);
        u.setPassword(passwordEncoder.encode(password));
        userRepository.save(u);
        return "Saved";
    }

}
