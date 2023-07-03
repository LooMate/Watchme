package com.easy2remember.controllerModule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//@ImportResource("classpath:applicationContext.xml")
@ComponentScan(basePackages = "com.easy2remember")
@SpringBootApplication
public class PosterBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(PosterBackApplication.class, args);
    }

}
