package com.tsurkan.MyBootApp.service;

import com.tsurkan.MyBootApp.domain.study.Portion;
import com.tsurkan.MyBootApp.domain.study.Topic;
import com.tsurkan.MyBootApp.repo.StudyRerository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

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

    public Portion createPortion(String content, String title, MultipartFile file, String uploadPath) throws IOException {

        Topic topic = new Topic(title);
        Portion portion = new Portion(content);
        portion.setTopic(topic);

        /*if(file!=null){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resName = uuidFile + file.getOriginalFilename();
            file.transferTo(new File(uploadPath+"/"+resName));
            portion.setFilename(resName);
        }*/

        return portion;
    }
}
