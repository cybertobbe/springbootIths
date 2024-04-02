package com.example.springbootprojektiths.config;

import com.example.springbootprojektiths.entity.User;
import com.example.springbootprojektiths.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends DefaultOAuth2UserService {

    Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    private final UserRepository userRepository;

    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    SecurityFilterChain web(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/homepage").permitAll();
                    auth.anyRequest().authenticated();

                })
                .oauth2Login(Customizer.withDefaults())
                .build();

    }


    @Bean
    static RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER\n" +
                "ROLE_USER > ROLE_GUEST");
        return hierarchy;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oidcUser = super.loadUser(userRequest);
        Map<String, Object> attributes = oidcUser.getAttributes();
        logger.info("Attributes: {}", attributes);
        User gitHubUser = new User();

        Object idObject = attributes.get("id");
        if (idObject instanceof Integer) {
            Integer idInteger = (Integer) idObject;
            gitHubUser.setId(idInteger.longValue());
        } else if (idObject instanceof Long) {
            gitHubUser.setId((Long) idObject);
        } else {
            // Handle the case where the id is neither Integer nor Long
            logger.error("Unexpected type for id attribute: {}", idObject.getClass());
        }
        gitHubUser.setFullName((String) attributes.get("name"));
    //    gitHubUser.setUrl((String) attributes.get("html_url"));
//        gitHubUser.setLogin((String) attributes.get("login"));
        userRepository.save(gitHubUser);
      // updateUser(gitHubUser);
        return oidcUser;
    }
    private void updateUser(User gitUser) {
        logger.info("User detected, {}, {}", gitUser.getFullName(), gitUser.getId());
        //var user = userInfoRepository.findByUserId(gitUser.getId());

        if (gitUser == null) {
            logger.info("New user detected, {}, {}", gitUser.getFullName(), gitUser.getId());
            //user = new UserInfo();
            gitUser = new User();
        }
        userRepository.save(gitUser);
       // user.ifPresent(u -> userRepository.save(u));
}
}


