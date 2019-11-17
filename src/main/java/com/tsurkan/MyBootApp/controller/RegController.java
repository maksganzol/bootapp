package com.tsurkan.MyBootApp.controller;

import com.tsurkan.MyBootApp.domain.Role;
import com.tsurkan.MyBootApp.domain.User;
import com.tsurkan.MyBootApp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class RegController {

    private UserService userService;

    public RegController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "registration")
    public String registration(){
        return "registration";
    }

    @PostMapping(value = "registration")
    public String regUser(User user, Model model){
        User us = (User) userService.loadUserByUsername(user.getLogin());
        if(us!=null){
            model.addAttribute("message", "User already exists");
            return "registration";
        }
        user.setRoles(Collections.singleton(Role.STUDENT));
        user.setActive(true);
        userService.saveUser(user);
        return "redirect:/login";

    }
}
