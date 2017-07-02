package com.aworkingapp.server.utils;

import com.aworkingapp.server.rest.model.RegisterUserModel;
import org.apache.commons.validator.routines.EmailValidator;

/**
 * Created by chen on 2017-06-29.
 */
public class UserChecker {

    public static boolean validRegisterUserModel(RegisterUserModel registerUserModel){
        if (!validPassword(registerUserModel.getPassword())){
           return false;
        }

        return EmailValidator.getInstance().isValid(registerUserModel.getEmail());
    }

    public static boolean validPassword(String password){
        return password.length() > 5;
    }
}
