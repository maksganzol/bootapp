package com.tsurkan.MyBootApp.service;

import com.tsurkan.MyBootApp.domain.study.Portion;
import com.tsurkan.MyBootApp.domain.study.Topic;
import com.tsurkan.MyBootApp.repo.StudyRerository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class StudyService {

    private final StudyRerository studyRerository;

    public StudyService(StudyRerository studyRerository) {
        this.studyRerository = studyRerository;
    }

    public List<Portion> getAllPortions(){
        ArrayList<Portion> portions = new ArrayList<>();
        studyRerository.findAll().forEach(portions::add);
        return portions;
    }

    public Set<Topic> getAllTopics(){
        Set<Topic> topics = new HashSet<>();
        studyRerository.findAll().forEach(portion -> {
            topics.add(portion.getTopic());
        });
        return topics;
    }
    public List<Portion> getPortionsByTopic(Topic topic){
        ArrayList<Portion> portions = new ArrayList<>();
        for(Portion p : studyRerository.findAll()){
            if(p.getTopic().equals(topic))
                portions.add(p);
        }
        return portions;
    }

    public void add(Portion portion){
        studyRerository.save(portion);
    }

    public void delete(Portion portion){
        studyRerository.deleteById(portion.getId());
    }
}
