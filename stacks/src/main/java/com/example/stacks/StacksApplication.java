package com.example.stacks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class StacksApplication {

    public static void main(String[] args) {
        SpringApplication.run(StacksApplication.class, args);
    }

}
