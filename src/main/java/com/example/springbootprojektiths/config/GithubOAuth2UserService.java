package com.example.springbootprojektiths.config;


import com.example.springbootprojektiths.entity.User;
import com.example.springbootprojektiths.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GithubOAuth2UserService extends DefaultOAuth2UserService {

    Logger logger = LoggerFactory.getLogger(GithubOAuth2UserService.class);

    GitHubService gitHubService;
    UserRepository userRepository;

    public GithubOAuth2UserService(GitHubService gitHubService, UserRepository userRepository) {
        this.gitHubService = gitHubService;
        this.userRepository = userRepository;
    }


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oidcUser = super.loadUser(userRequest);
        Map<String, Object> attributes = oidcUser.getAttributes();
        logger.info("Attributes: {}", attributes);
        // Long gitHubUserId = Long.parseLong((String) attributes.get("id"));
        Object idObject = attributes.get("id");
        Integer idInteger = (Integer) idObject;
        Optional<User> optionalUser = userRepository.findById(idInteger.longValue());

        User gitHubUser;
        if (optionalUser.isPresent()) {
            gitHubUser = optionalUser.get();

        } else {
            OAuth2AccessToken accessToken = userRequest.getAccessToken();
            gitHubUser = new User();
            gitHubUser.setId(Long.valueOf(idInteger));
            String fullName = (String) attributes.get("name");
            gitHubUser.setFullName(fullName);
            String userName = (String) attributes.get("login");
            System.out.println(userName);
            gitHubUser.setUserName(userName);
            List<Email> result = gitHubService.getEmails(accessToken);

            for (Email email : result) {
                if (email.primary()) {
                    String emailAddress = email.email();
                    System.out.println("Primary Email Address: " + emailAddress);
                    gitHubUser.setMail(emailAddress);
                    break;
                }
            }
        }
       // updateChangedUserInfo(gitHubUser, attributes);

        userRepository.save(gitHubUser);

        return oidcUser;
    }

    private void updateChangedUserInfo(User gitHubUser, Map<String, Object> attributes) {
        String fullName = (String) attributes.get("name");
        if (fullName != null && !fullName.equals(gitHubUser.getFullName())) {
            //gitHubUser.setFullName(fullName);
        }
        else {
            gitHubUser.setFullName(fullName);
        }

    }




}
