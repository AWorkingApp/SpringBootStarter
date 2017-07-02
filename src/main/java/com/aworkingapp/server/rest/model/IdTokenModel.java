package com.aworkingapp.server.rest.model;

/**
 * Created by chen on 2017-06-30.
 */
public class IdTokenModel {

    private String idToken;
    private long expiresAt;

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public long getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(long expiresAt) {
        this.expiresAt = expiresAt;
    }
}
