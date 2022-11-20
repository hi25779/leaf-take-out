package com.lhy.leaftakeout;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
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
