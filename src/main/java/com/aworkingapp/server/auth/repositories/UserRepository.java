package com.aworkingapp.server.auth.repositories;

import com.aworkingapp.server.auth.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by chen on 5/31/17.
 */
@Repository
public interface UserRepository extends MongoRepository<User, ObjectId>{
    User findOneByUsername(String username);

    User findOneByUserId(String userId);
}
