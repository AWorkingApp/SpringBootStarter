package com.aworkingapp.sbstarter.rest;

import com.aworkingapp.sbstarter.auth.model.User;
import com.aworkingapp.sbstarter.auth.service.UserRequestInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by chen on 5/31/17.
 */
@RestController
@RequestMapping("/admin")
public class AdminResource {

    @Autowired
    UserRequestInfoService userRequestInfoService;

    @GetMapping("/me")
    public ResponseEntity<User> me(){
       User currUser = userRequestInfoService.getRequestUser();

       return ResponseEntity.ok(currUser);
    }
}
