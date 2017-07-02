package com.aworkingapp.server.service;

import com.aworkingapp.server.auth.common.Constants;
import com.aworkingapp.server.auth.model.User;
import com.aworkingapp.server.auth.repositories.UserRepository;
import com.aworkingapp.server.rest.UserResource;
import com.aworkingapp.server.rest.model.RegisterUserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by chen on 2017-06-29.
 */
@Service
public class AccountService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public User registerUser(RegisterUserModel registerUserModel) {

        // create uuid with provider as user id
        User user = new User(Constants.PROVIDER + "|" + UUID.randomUUID().toString());

        user.setUsername(registerUserModel.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(registerUserModel.getPassword()));
        user.setEmail(registerUserModel.getEmail());

        User createdUser = userRepository.save(user);
        return createdUser;
    }

    public User getUserByUserId(String userId){
        return userRepository.findOneByUserId(userId);
    }
}
