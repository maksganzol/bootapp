package com.tsurkan.MyBootApp.controller;

import com.tsurkan.MyBootApp.domain.Role;
import com.tsurkan.MyBootApp.domain.User;
import com.tsurkan.MyBootApp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.Null;
import java.util.Collections;

@Controller
@RequestMapping(value = "/registration")
public class RegController {

    private UserService userService;

    public RegController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String registration(){
        return "registration";
    }

    @PostMapping
    public String regUser(User user, Model model){
        User us = (User) userService.getUserByLogin(user.getLogin());
        if(us !=null){
            model.addAttribute("message", "User already exists");
            return "registration";
        }
        user.setRoles(Collections.singleton(Role.STUDENT));
        user.setActive(true);
        userService.saveUser(user);
        return "redirect:/login";

    }
}
