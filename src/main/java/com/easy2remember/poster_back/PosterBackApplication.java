package com.easy2remember.poster_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

//@ImportResource("classpath:applicationContext.xml")
@SpringBootApplication
public class PosterBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(PosterBackApplication.class, args);
    }

}
