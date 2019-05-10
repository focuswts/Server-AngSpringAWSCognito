package com.spring.angular;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class AngularApplication {

    public static void main(String[] args) {
        SpringApplication.run(AngularApplication.class, args);
    }


}

