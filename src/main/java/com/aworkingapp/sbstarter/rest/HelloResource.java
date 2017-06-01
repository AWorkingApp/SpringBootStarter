package com.aworkingapp.sbstarter.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by chen on 5/31/17.
 */
@RestController()
@RequestMapping("/api")
public class HelloResource {

    @GetMapping("/pub")
    public String index() {
        return "Greetings from Spring Boot!";
    }

}
