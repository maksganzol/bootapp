package com.tsurkan.MyBootApp.controller;

import com.tsurkan.MyBootApp.domain.Role;
import com.tsurkan.MyBootApp.domain.User;
import com.tsurkan.MyBootApp.helper.AuthenticationHelper;
import com.tsurkan.MyBootApp.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller

public class UserController {

   private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userList(Model model){
        model.addAttribute("users", userService.getAll());
        model.addAttribute("auth", AuthenticationHelper.getAuthInString());
        return "userList";
    }

    @GetMapping(value = "/user/{user}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String update(@PathVariable User user, Model model){
        model.addAttribute("auth", AuthenticationHelper.getAuthInString());
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PostMapping("/user")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String apply(@RequestParam(value = "roles" , required = false) String[] roles,
                        @RequestParam("userId") User user,
                        @RequestParam String login,
                        @RequestParam String password,
                        @RequestParam String name,
                        @RequestParam String lastName,
                        Model model){

        model.addAttribute("auth", AuthenticationHelper.getAuthInString());
        userService.update(user, name, lastName, login, password, roles);
        userService.saveUser(user);
        return "redirect:/user";
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
        userService.saveUser(user);
        model.addAttribute("auth", AuthenticationHelper.getAuthInString());
        model.addAttribute("user", user);
        return "profile";
    }
}
