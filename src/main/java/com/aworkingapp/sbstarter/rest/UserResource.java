package com.aworkingapp.sbstarter.rest;

import com.aworkingapp.sbstarter.auth.model.User;
import com.aworkingapp.sbstarter.auth.model.UserAuthority;
import com.aworkingapp.sbstarter.auth.model.UserRole;
import com.aworkingapp.sbstarter.auth.service.UserRequestInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by chen on 6/1/17.
 */
@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasAuthority('ROLE_USER')")
public class UserResource {

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
