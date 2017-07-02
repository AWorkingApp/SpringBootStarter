package com.aworkingapp.server.auth.service;

import com.aworkingapp.server.auth.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Chen.Liu on 06/10/2015.
 */
@Service
public class UserRequestInfoService {
    public User getRequestUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user;
        try {
            user = (User) auth.getDetails();
        }
        catch(ClassCastException cce){
            throw new UsernameNotFoundException("unknown user");
        }
        return user;
    }

    public String getRequestUsername(){
        User user = getRequestUser();
        String username = user.getUsername();
        if(StringUtils.isEmpty(username)){
            throw new IllegalArgumentException("username is empty");
        }
        return username;
    }

    public String getRequestUserId(){
        User user = getRequestUser();
        String userId = user.getUserId();
        if(StringUtils.isEmpty(userId)){
            throw new IllegalArgumentException("userId is empty");
        }
        return userId;
    }

    public String getRequestIP(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        return request.getRemoteAddr();
    }
}
