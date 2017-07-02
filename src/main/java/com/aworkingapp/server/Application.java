package com.aworkingapp.server;

import com.aworkingapp.server.auth.AuthProperties;
import com.aworkingapp.server.auth.TokenHandler;
import com.aworkingapp.server.auth.common.Constants;
import com.aworkingapp.server.auth.model.User;
import com.aworkingapp.server.auth.model.UserRole;
import com.aworkingapp.server.auth.repositories.UserRepository;
import com.aworkingapp.server.config.properties.ApplicationProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.xml.bind.DatatypeConverter;
import java.util.List;
import java.util.UUID;

/**
 * Created by chen on 5/31/17.
 */
@SpringBootApplication
@EnableConfigurationProperties({AuthProperties.class, ApplicationProperties.class})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    @Autowired
//    UserRepository userRepository;

//    @Bean
//    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        return args -> {
////            User admin = new User();
////            admin.setUsername("admin");
////            admin.setPassword(bCryptPasswordEncoder.encode("admin"));
////            admin.grantRole(UserRole.ADMIN);
//
//            List<User> users = userRepository.findAll();
//            users.forEach(user -> {
//                userRepository.delete(user);
//            });
//
//            User user = new User(Constants.PROVIDER + "|" + UUID.randomUUID().toString());
//            user.setUsername("user");
//            user.setPassword(bCryptPasswordEncoder.encode("user"));
//            user.setEmail("user");
//            user.grantRole(UserRole.USER);
//
////            userRepository.save(admin);
//            userRepository.save(user);
//        };
//    }

    @Value("${token.secret.user}") String secret;
    @Value("{token.secret.refresh}") String refreshSecret;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    @Bean
    public TokenHandler tokenHandler(){
      TokenHandler tokenHandler = new TokenHandler(DatatypeConverter.parseBase64Binary(secret));

      return tokenHandler;
    }
}
