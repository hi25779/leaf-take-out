package com.lhy.leaftakeout;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LeafTakeOutApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(LeafTakeOutApplication.class, args);
            System.out.println("SpringBoot Start....");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
