package com.aworkingapp.server.auth.repositories;

import com.aworkingapp.server.Application;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by chen on 2017-06-29.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserRepositoryTests {

    @Test
    public void pass(){
        Assert.assertTrue(true);
    }

}
