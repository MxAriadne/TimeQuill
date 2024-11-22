package com.hedgehog.timequill.controllers;

import com.hedgehog.timequill.entities.UserEntity;
import com.hedgehog.timequill.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Iterator;
import java.util.Set;

import static java.lang.Integer.parseInt;

@Controller
public class AdminController {

    @Autowired
    UserRepository userRepo;

    @GetMapping("/account/admin")
    public String adminView(Model model) {
        Iterable<UserEntity> users = userRepo.findAll();

        model.addAttribute("users", users);

        return "/account/admin";
    }

    @PostMapping("/account/delete")
    public String delete(@RequestParam String userId) {
        UserEntity user = userRepo.findById(parseInt(userId)).get();
        userRepo.delete(user);
        return "redirect:/account/admin";
    }

    @PostMapping("/account/admin/control-access")
    public String controlAccess(@RequestParam String userId) {
        UserEntity user = userRepo.findByUsername(userId);
        user.setLocked(!user.getLocked());
        userRepo.save(user);
        return "redirect:/account/admin";
    }

    @PostMapping("/account/admin/update-user")
    public String assignManager(@RequestParam String userId, @RequestParam String role) {
        UserEntity user = userRepo.findByUsername(userId);
        if (role.equals("Manager")) {
            user.setManager(true);
        } else {
            System.out.println(role);
            user.setManager(false);
        }
        userRepo.save(user);
        return "redirect:/account/admin";
    }

}
