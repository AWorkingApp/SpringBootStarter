package com.aworkingapp.server.auth.model;

import com.aworkingapp.server.domain.AbsMongoModel;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

/**
 * Created by chen on 2017-06-29.
 */
@Document(collection = "refresh_token")
public class RefreshToken extends AbsMongoModel {

    @Indexed
    private String refreshToken;

    @Indexed
    private String userId;

    //TODO: add a expired time for refresh token
//    private long expiresAt;

    @JsonCreator
    public RefreshToken(String userId){
        super();
        this.refreshToken = UUID.randomUUID().toString();
        this.userId = userId;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
