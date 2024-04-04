package com.example.springbootprojektiths.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestClient;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain web(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests(authorize -> {
                    authorize.requestMatchers("/homepage").permitAll();
                    authorize.anyRequest().authenticated();
                })
                .oauth2Login(oauth2 -> oauth2
                        .defaultSuccessUrl("/listmessages") // Redirect URL after successful login
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/homepage") // Redirect URL after logout
                        .permitAll()
                )
                .build();
    }

    @Bean
    RestClient restClient() {
        return RestClient.create();
    }
}

