package com.greenplan.app;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.greenplan")
@EnableJpaRepositories(basePackages = "com.greenplan")
@EntityScan(basePackages = "com.greenplan")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}