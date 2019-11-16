package com.tsurkan.MyBootApp.controller;

import com.tsurkan.MyBootApp.domain.Role;
import com.tsurkan.MyBootApp.domain.User;
import com.tsurkan.MyBootApp.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class RegController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "registration")
    public String registration(){
        return "registration";
    }

    @PostMapping(value = "registration")
    public String regUser(User user, Model model){
        User us = userRepository.findByLogin(user.getLogin());
        if(us!=null){
            model.addAttribute("message", "User already exists");
            return "registration";
        }
        user.setRoles(Collections.singleton(Role.STUDENT));
        user.setActive(true);
        userRepository.save(user);
        return "redirect:/login";

    }
}
