package com.tsurkan.MyBootApp.controller;

import com.tsurkan.MyBootApp.domain.study.Portion;
import com.tsurkan.MyBootApp.domain.study.Topic;
import com.tsurkan.MyBootApp.helper.AuthenticationHelper;
import com.tsurkan.MyBootApp.service.StudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/material")
@PreAuthorize("hasAuthority('TEACHER')")
public class MaterialController {

    @Autowired
    private HttpServletRequest request;

    @Value("${upload.path}")
    private String uploadPath;
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
    public String addMaterial(@RequestParam String content,
                              @RequestParam String title,
                              @RequestParam("file") MultipartFile file,
                              Model model) throws IOException {
        Portion portion = studyService.createPortion(content, title, file, request.getServletContext().getRealPath(uploadPath));
        studyService.add(portion);
        model.addAttribute("auth", AuthenticationHelper.getAuthInString());
        model.addAttribute("portions", studyService.getAllPortions());
        return "material";
    }

    @GetMapping("{id}")
    public String delete(@PathVariable("id") Portion portion){
        studyService.delete(portion);
        return "redirect:/material";
    }
}
