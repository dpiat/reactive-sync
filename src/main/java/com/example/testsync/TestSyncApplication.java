package com.example.testsync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TestSyncApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestSyncApplication.class, args);
    }

}
