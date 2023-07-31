package com.web;

import com.web.config.StorageProperties;
import com.web.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class WomenShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(WomenShopApplication.class, args);
    }
    @Bean
    CommandLineRunner init(StorageService storageService) {
        return(args->{
            storageService.init();
        });
    }
}