package com.aworkingapp.server.rest.model;

/**
 * Created by chen on 2017-06-30.
 */
public class VerifyTokenModel {

    private String site;

    private String userId;

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
