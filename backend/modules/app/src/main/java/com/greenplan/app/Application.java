package com.greenplan.app;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;

@SpringBootApplication(scanBasePackages = "com.greenplan")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}