package com.example.springbootprojektiths;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpringbootProjektIthsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootProjektIthsApplication.class, args);
    }

}
