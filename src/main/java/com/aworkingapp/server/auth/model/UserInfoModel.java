package com.aworkingapp.server.auth.model;

import com.aworkingapp.server.auth.common.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by chen on 2017-06-30.
 */
public class UserInfoModel {

    public UserInfoModel(){
        this.provider = Constants.PROVIDER;
    }

    private String provider;
    private long expiresAt;
    private String idToken;
    private String refreshToken;
    private String tokenType;

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public long getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(long expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
