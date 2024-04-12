package com.example.springbootprojektiths;

//Before first run.
//Add CLIENT_ID and CLIENT_SECRET to enviraonment variablesin run configuration.

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
