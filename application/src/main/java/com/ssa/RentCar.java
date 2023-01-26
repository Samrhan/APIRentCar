package com.ssa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.ssa.*"})
public class RentCar {

    public static void main(String[] args) {
        SpringApplication.run(RentCar.class, args);
    }
}
