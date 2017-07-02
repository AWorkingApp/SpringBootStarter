package com.aworkingapp.server.domain;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by chen on 2017-06-29.
 */
public class ConfirmEmailToken{

    private String email;
    private long expireAt;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(long expireAt) {
        this.expireAt = expireAt;
    }
}
