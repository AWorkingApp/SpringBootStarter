package com.aworkingapp.server.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by chen on 5/31/17.
 */
@ConfigurationProperties(prefix = "auth", ignoreUnknownFields = false)
public class AuthProperties {
    public String userSecret;
    public String refreshSecret;

    public String getUserSecret() {
        return userSecret;
    }

    public void setUserSecret(String userSecret) {
        this.userSecret = userSecret;
    }

    public String getRefreshSecret() {
        return refreshSecret;
    }

    public void setRefreshSecret(String refreshSecret) {
        this.refreshSecret = refreshSecret;
    }
}
