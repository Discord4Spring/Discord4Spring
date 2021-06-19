package com.discord4spring.discord4spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class Discord4SpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(Discord4SpringApplication.class, args);
    }
}
