package com.tsurkan.MyBootApp.controller;

import com.tsurkan.MyBootApp.domain.study.Portion;
import com.tsurkan.MyBootApp.domain.study.Topic;
import com.tsurkan.MyBootApp.helper.AuthenticationHelper;
import com.tsurkan.MyBootApp.repo.StudyRerository;
import com.tsurkan.MyBootApp.service.StudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.sound.sampled.Port;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "/study")
public class StudyController {
    @Autowired
    private StudyService studyService;

    @GetMapping
    public String study(Model model) {

        model.addAttribute("topics", studyService.getAllTopics());
        model.addAttribute("auth", AuthenticationHelper.getAuthInString());
        return "studying";
    }

    @GetMapping(value = "{topic}")
    public String getTopic(@PathVariable String topic, Model model) {

        List<Portion> portions = studyService.getPortionsByTopic(new Topic(topic));
        Set<Topic> topics = studyService.getAllTopics();
        model.addAttribute("auth", AuthenticationHelper.getAuthInString());
        model.addAttribute("topics", topics);
        model.addAttribute("portions", portions);
        model.addAttribute("topic", new Topic(topic));

        return "studying";
    }
}
