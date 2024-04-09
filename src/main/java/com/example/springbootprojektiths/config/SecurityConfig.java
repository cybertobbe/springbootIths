package com.example.springbootprojektiths.config;


import com.example.springbootprojektiths.entity.User;
import com.example.springbootprojektiths.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.client.RestClient;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain web(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/homepage", "/login", "/oauth/**", "/logout", "/error**").permitAll();
                    authorize.requestMatchers(customRequestMatcher("/static/style.css")).permitAll();
                    authorize.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll();
                    authorize.anyRequest().authenticated();
                })
                .oauth2Login(oauth2 -> oauth2.defaultSuccessUrl("/listmessages").permitAll())
                .logout(logout -> logout
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.sendRedirect("/homepage"); // Redirect to homepage after logout
                        })
                        .permitAll())
                .build();
    }


    private RequestMatcher customRequestMatcher(String path) {
        return request -> path.equals(request.getServletPath());
    }

    @Bean
    static RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER\n" +
                "ROLE_USER > ROLE_GUEST");
        return hierarchy;
    }


    @Bean
    RestClient restClient() {
        return RestClient.create();
    }
}

