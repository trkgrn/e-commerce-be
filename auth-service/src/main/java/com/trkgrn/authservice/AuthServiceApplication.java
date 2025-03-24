package com.trkgrn.authservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.trkgrn")
public class AuthServiceApplication{

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

}
