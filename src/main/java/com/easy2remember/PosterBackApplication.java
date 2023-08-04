package com.easy2remember;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@EnableAspectJAutoProxy(proxyTargetClass = true)
@SpringBootApplication
public class PosterBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(PosterBackApplication.class, args);
    }

}
