package com.aworkingapp.sbstarter;

import com.aworkingapp.sbstarter.auth.AuthProperties;
import com.aworkingapp.sbstarter.auth.model.User;
import com.aworkingapp.sbstarter.auth.model.UserRole;
import com.aworkingapp.sbstarter.auth.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;

/**
 * Created by chen on 5/31/17.
 */
@SpringBootApplication
@EnableConfigurationProperties({AuthProperties.class})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    UserRepository userRepository;

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return args -> {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(bCryptPasswordEncoder.encode("admin"));
            admin.grantRole(UserRole.ADMIN);

            User user = new User();
            user.setUsername("user");
            user.setPassword(bCryptPasswordEncoder.encode("user"));
            user.grantRole(UserRole.USER);

            userRepository.save(admin);
            userRepository.save(user);
        };
    }
}
