package com.trkgrn.productgalleryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.trkgrn")
public class ProductGalleryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductGalleryServiceApplication.class, args);
    }

}
