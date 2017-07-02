package com.aworkingapp.server.auth.service;

import com.aworkingapp.server.auth.model.RefreshToken;
import com.aworkingapp.server.auth.model.User;
import com.aworkingapp.server.auth.repositories.RefreshTokenRepository;
import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * Created by chen on 2017-06-30.
 */
@Service
public class RefreshTokenService {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    public RefreshToken createRefreshToken(User user){

        RefreshToken refreshToken = new RefreshToken(user.getUserId());
        refreshToken = refreshTokenRepository.save(refreshToken);

        return refreshToken;
    }

    public boolean validRefreshToken(String token, String userId){
        RefreshToken currToken = refreshTokenRepository.findByRefreshTokenAndUserId(token, userId);

        if(currToken == null){
           return false;
        }

        return true;
    }

    public boolean updateRefreshToken(String currToken, String userId, String newToken){

        WriteResult result = mongoTemplate.updateFirst(new Query(where("refreshToken").is(currToken).and("userId").is(userId)), new Update().set("refreshToken", newToken), RefreshToken.class);
        return result.wasAcknowledged() && result.getN() > 0;
    }

    public void revokeRefreshToken(String token, String userId){
        // delete the token
        refreshTokenRepository.deleteByRefreshTokenAndUserId(token, userId);
    }
}
