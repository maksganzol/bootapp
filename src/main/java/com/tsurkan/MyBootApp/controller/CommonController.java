package com.tsurkan.MyBootApp.controller;

import com.tsurkan.MyBootApp.domain.User;
import com.tsurkan.MyBootApp.helper.AuthenticationHelper;
import com.tsurkan.MyBootApp.repo.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CommonController {
    private UserRepository userRepository;

    public CommonController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("auth", AuthenticationHelper.getAuthInString());
        return "main";
    }
    @GetMapping("/rules")
    public String rules(Model model){
        model.addAttribute("auth", AuthenticationHelper.getAuthInString());
        return "rules";
    }
    @GetMapping("/profile")
    public String profile(Model model, @AuthenticationPrincipal User user){
        model.addAttribute("auth", AuthenticationHelper.getAuthInString());
        model.addAttribute("user", user);
        return "profile";
    }
    @PostMapping("/profile")
    public String edit(@RequestParam String name,
            @RequestParam String lastName,
            @RequestParam String login,
            @RequestParam String password, Model model, @AuthenticationPrincipal User user){
        user.setLogin(login);
        user.setPassword(password);
        user.setName(name);
        user.setLastName(lastName);
        userRepository.save(user);
        model.addAttribute("auth", AuthenticationHelper.getAuthInString());
        model.addAttribute("user", user);
        return "profile";
    }
}
