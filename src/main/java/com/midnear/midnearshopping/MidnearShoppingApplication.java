package com.midnear.midnearshopping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MidnearShoppingApplication {

    public static void main(String[] args) {
        SpringApplication.run(MidnearShoppingApplication.class, args);
    }

}
