package com.tsurkan.MyBootApp.controller;

import com.tsurkan.MyBootApp.domain.study.Portion;
import com.tsurkan.MyBootApp.domain.study.Topic;
import com.tsurkan.MyBootApp.helper.AuthenticationHelper;
import com.tsurkan.MyBootApp.service.StudyService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/material")
public class MaterialController {

    private StudyService studyService;

    public MaterialController(StudyService studyService) {
        this.studyService = studyService;
    }

    @GetMapping
    public String material(Model model){
        model.addAttribute("auth", AuthenticationHelper.getAuthInString());
        model.addAttribute("portions", studyService.getAllPortions());
        return "material";
    }

    @PostMapping
    public String addMaterial(@RequestParam String content, @RequestParam String title, Model model) {
        model.addAttribute("auth", AuthenticationHelper.getAuthInString());
        Topic topic = new Topic(title);
        Portion portion = new Portion(content);
        portion.setTopic(topic);
        studyService.add(portion);
        model.addAttribute("portions", studyService.getAllPortions());
        return "material";
    }
    @GetMapping("{id}")
    public String delete(@PathVariable("id") Portion portion){
        studyService.delete(portion);
        return "redirect:/material";
    }
}
