package com.midnear.midnearshopping;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.TimeZone;

@SpringBootApplication
@EnableScheduling
public class MidnearShoppingApplication {

    public static void main(String[] args) {
        SpringApplication.run(MidnearShoppingApplication.class, args);
    }

    @PostConstruct
    void started(){
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }
}
