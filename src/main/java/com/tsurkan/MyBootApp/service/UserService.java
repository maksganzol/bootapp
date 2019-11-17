package com.tsurkan.MyBootApp.service;


import com.tsurkan.MyBootApp.domain.Role;
import com.tsurkan.MyBootApp.domain.User;
import com.tsurkan.MyBootApp.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserDetails user = userRepository.findByLogin(login);
        if(user==null) throw new UsernameNotFoundException("There are not user with such login");
        return user;
    }

    public User getUserByLogin(String login){
        return userRepository.findByLogin(login);
    }

    public void saveUser(User user){
        userRepository.save(user);
        System.out.println(user);
    }

    public void deleteUser(User user){
        userRepository.delete(user);
    }
    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }

    public void update(User user, String name, String lastName, String login, String password, String[] roles){
        Set<String> roleVal = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());
        user.getRoles().clear();
        if(roles != null) {
            for (String role : roles) {
                if (roleVal.contains(role)) {
                    user.getRoles().add(Role.valueOf(role));
                }
            }
        }
        user.setName(name);
        user.setLastName(lastName);
        user.setLogin(login);
        user.setPassword(password);
    }

    public Iterable<User> getAll(){
        return userRepository.findAll();
    }

}
