package com.example.spring_boot_ex_04;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.example.spring_boot_ex_04") 
@EntityScan("com.example.spring_boot_ex_04")
@SpringBootApplication
public class SpringBootEx04Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootEx04Application.class, args);
    }

}