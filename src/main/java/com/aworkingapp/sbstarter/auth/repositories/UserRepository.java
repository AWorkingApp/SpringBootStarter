package com.aworkingapp.sbstarter.auth.repositories;

import com.aworkingapp.sbstarter.auth.model.User;

/**
 * Created by chen on 5/31/17.
 */
public interface UserRepository {
    User findByUsername(String username);

    User save(User user);
}
