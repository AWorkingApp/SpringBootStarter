package com.aworkingapp.server.rest;

import com.aworkingapp.server.auth.model.User;
import com.aworkingapp.server.rest.model.RegisterUserModel;
import com.aworkingapp.server.service.AccountService;
import com.aworkingapp.server.utils.UserChecker;
import com.mongodb.DuplicateKeyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

/**
 * Created by chen on 2017-06-29.
 */
@RestController
@RequestMapping("/api/auth/v1/account")
public class AccountResource {

    private static Logger LOGGER = LoggerFactory.getLogger(AccountResource.class);

    @Autowired
    AccountService accountService;

    // register account
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterUserModel registerUserModel){

        try {
            if(!UserChecker.validRegisterUserModel(registerUserModel)){
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
            }

            User user = accountService.registerUser(registerUserModel);

            return ResponseEntity.created(URI.create(user.getId())).build();
        } catch (DuplicateKeyException dke) {
            // remove password if fail
            LOGGER.error("error register user: {}, duplicate key message: {}", registerUserModel, dke.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e){
            // remove password if fail
            LOGGER.error("error register user: {}, message: {}", registerUserModel, e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
