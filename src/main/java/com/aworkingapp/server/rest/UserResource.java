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
 * Created by chen on 6/1/17.
 */
@RestController
@RequestMapping("/api/auth/v1/user")
public class UserResource {

   @Autowired
   UserRequestInfoService userRequestInfoService;

   @GetMapping("/me")
   public ResponseEntity<User> me(){
      User currUser = userRequestInfoService.getRequestUser();
      return ResponseEntity.ok(currUser);
   }

}
