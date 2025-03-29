package com.svwh.phonereview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.svwh.phonereview.*"})
public class PhoneReviewApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhoneReviewApplication.class, args);
    }

}
