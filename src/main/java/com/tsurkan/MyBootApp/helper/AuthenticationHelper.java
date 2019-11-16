package com.tsurkan.MyBootApp.helper;

import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

public class AuthenticationHelper {
    public static List<String> getAuthInString(){
        List<String> auth = new ArrayList<>();
        SecurityContextHolder.getContext().getAuthentication().getAuthorities().forEach(r ->{
            auth.add(r.getAuthority());
        });
        return auth;
    }
    public static String getLogin(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
