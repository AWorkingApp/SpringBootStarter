package com.aworkingapp.server.rest.model;

import javax.validation.constraints.NotNull;

/**
 * Created by chen on 2017-06-29.
 */
public class RegisterUserModel {

    // TODO: add more
    @NotNull
    private String username;
    @NotNull
    private String email;
    @NotNull
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("User name:")
                .append(username)
                .append("; ")
                .append("email: ")
                .append(email)
                .append(".");

        return stringBuilder.toString();
    }
}
