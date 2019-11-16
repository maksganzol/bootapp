package com.tsurkan.MyBootApp.service;

import com.tsurkan.MyBootApp.domain.Role;
import com.tsurkan.MyBootApp.domain.User;
import com.tsurkan.MyBootApp.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    private UserRepository userRepository;

    public StudentService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getStudents(){
        List<User> students = new ArrayList<>();
        userRepository.findAll().forEach(user -> {
            if(user.getRoles().contains(Role.STUDENT)){
                students.add(user);
            }
        });
        return students;
    }

    public void delete(User student){
        userRepository.deleteById(student.getId());
    }

    public User getStudentByLogin(String login){
        User user = userRepository.findByLogin(login);
        if(!user.getRoles().contains(Role.STUDENT))
            return null;
        return user;
    }

    public void updateStudent(User student) throws Exception {
        if(!student.getRoles().contains(Role.STUDENT))
            throw new Exception("User is not student");
        userRepository.save(student);
    }
}
