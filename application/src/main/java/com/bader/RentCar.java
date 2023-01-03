package com.bader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.bader.*"})
public class RentCar {

    public static void main(String[] args) {
        SpringApplication.run(RentCar.class, args);
    }
}
