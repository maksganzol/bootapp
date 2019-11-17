package com.tsurkan.MyBootApp.controller;

import com.tsurkan.MyBootApp.helper.AuthenticationHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CommonController {

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

}
