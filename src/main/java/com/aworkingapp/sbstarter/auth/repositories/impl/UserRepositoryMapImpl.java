package com.aworkingapp.sbstarter.auth.repositories.impl;

import com.aworkingapp.sbstarter.auth.model.User;
import com.aworkingapp.sbstarter.auth.repositories.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by chen on 5/31/17.
 */
@Repository
public class UserRepositoryMapImpl implements UserRepository{

    private static final Map<String, User> users = new ConcurrentHashMap<>();

    private static final Object _LOCK = new Object();

    @Override
    public User findByUsername(String username) {
        if (users.containsKey(username)){
            return users.get(username);
        }
        return null;
    }

    @Override
    public User save(User user) {
        synchronized (_LOCK) {
            users.put(user.getUsername(), user);
        }

        return user;
    }
}
