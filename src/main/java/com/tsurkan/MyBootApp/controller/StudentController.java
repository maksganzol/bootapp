package com.tsurkan.MyBootApp.controller;

import com.tsurkan.MyBootApp.domain.User;
import com.tsurkan.MyBootApp.helper.AuthenticationHelper;
import com.tsurkan.MyBootApp.service.StudentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@PreAuthorize("hasAuthority('TEACHER')")
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping(value = "/student")
    public String user(Model model){
        model.addAttribute("auth", AuthenticationHelper.getAuthInString());
        model.addAttribute("students", studentService.getStudents());
        return "students";
    }

    @GetMapping(value = "/delete_student/{id}")
    public String delete(@PathVariable("id") User user, Model model){
        model.addAttribute("auth", AuthenticationHelper.getAuthInString());
        studentService.delete(user);
        return "redirect:/student";
    }



}
