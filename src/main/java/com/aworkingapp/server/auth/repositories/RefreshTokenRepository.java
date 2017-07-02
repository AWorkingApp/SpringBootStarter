package com.aworkingapp.server.auth.repositories;

import com.aworkingapp.server.auth.model.RefreshToken;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by chen on 2017-06-30.
 */
@Repository
public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String>{

    RefreshToken findByRefreshTokenAndUserId(String refreshToken, String userId);

    @DeleteQuery("{ '$and': [{'refreshToken' : ?0 }, {'userId' : ?1}]} ")
    void deleteByRefreshTokenAndUserId(String refreshToken, String userId);
}
