package com.tsurkan.MyBootApp.controller;

import com.tsurkan.MyBootApp.domain.Role;
import com.tsurkan.MyBootApp.domain.User;
import com.tsurkan.MyBootApp.helper.AuthenticationHelper;
import com.tsurkan.MyBootApp.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String userList(Model model){
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("auth", AuthenticationHelper.getAuthInString());
        return "userList";
    }

    @GetMapping(value = "{user}")
    public String update(@PathVariable User user, Model model){
        model.addAttribute("auth", AuthenticationHelper.getAuthInString());
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PostMapping
    public String apply(@RequestParam(value = "roles" , required = false) String[] roles,
                        @RequestParam("userId") User user,
                        @RequestParam String login,
                        @RequestParam String password,
                        @RequestParam String name,
                        @RequestParam String lastName,
                        Model model){
        user.setLogin(login);
        user.setPassword(password);
        user.setName(name);
        user.setLastName(lastName);

        model.addAttribute("auth", AuthenticationHelper.getAuthInString());
        Set<String> roleVal = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());
        user.getRoles().clear();

        if(roles != null) {
            for (String role : roles) {
                if (roleVal.contains(role)) {
                    user.getRoles().add(Role.valueOf(role));
                } else {
                    user.getRoles().remove(Role.valueOf(role));
                }
            }
        }
        userRepository.save(user);
        return "redirect:/user";
    }
}
