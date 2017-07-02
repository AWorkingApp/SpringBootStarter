package com.aworkingapp.server.rest;

import com.aworkingapp.server.auth.model.User;
import com.aworkingapp.server.auth.model.UserAuthority;
import com.aworkingapp.server.auth.model.UserRole;
import com.aworkingapp.server.auth.service.UserRequestInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by chen on 5/31/17.
 */
@RestController
@RequestMapping("/api/auth/v1/admin")
public class AdminResource {

    @Autowired
    UserRequestInfoService userRequestInfoService;

    @GetMapping("/me")
    public ResponseEntity<User> me(){
       User currUser = userRequestInfoService.getRequestUser();

       for (UserRole role: currUser.getRoles()){
           System.out.println(role);
       }

        for (UserAuthority authority: currUser.getAuthorities()){
            System.out.println(authority);
        }

       return ResponseEntity.ok(currUser);
    }
}
