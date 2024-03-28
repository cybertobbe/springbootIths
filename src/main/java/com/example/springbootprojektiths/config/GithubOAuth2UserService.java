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

    public GithubOAuth2UserService(GitHubService gitHubService,UserRepository userRepository) {
        this.gitHubService = gitHubService;
        this.userRepository = userRepository;
    }

//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        OAuth2User oidcUser = super.loadUser(userRequest);
//        Map<String, Object> attributes = oidcUser.getAttributes();
//
//       //logger.info("Attributes: {}", attributes);
//        Object idObject = attributes.get("id");
//
//        User gitHubUser = new User();
//        if (idObject instanceof Integer) {
//            Integer idInteger = (Integer) idObject;
//            gitHubUser.setId(idInteger.longValue());
//        } else if (idObject instanceof Long) {
//            gitHubUser.setId((Long) idObject);
//        } else {
//            logger.error("Unexpected type for id attribute: {}", idObject.getClass());
//        }
//
//
//        String fullName = gitHubUser.getFullName();
//        gitHubUser.setFullName((String) attributes.get("name"));
//        if (fullName == null || fullName.isEmpty()) {
//
//            gitHubUser.setFullName((String) attributes.get("name"));
//        }
//
//        OAuth2AccessToken accessToken = userRequest.getAccessToken();
//
//        List<Email> result = gitHubService.getEmails(accessToken);
//
//        for (Email email : result) {
//            if (email.primary()) {
//                String emailAddress = email.email();
//                System.out.println("Primary Email Address: " + emailAddress);
//                gitHubUser.setMail(emailAddress);
//                break;
//            }
//        }
//
//        userRepository.save(gitHubUser);
//        return oidcUser;
//    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oidcUser = super.loadUser(userRequest);
        Map<String, Object> attributes = oidcUser.getAttributes();
        // Long gitHubUserId = Long.parseLong((String) attributes.get("id"));
        Object idObject = attributes.get("id");
        Integer idInteger = (Integer) idObject;
        Optional<User> optionalUser = userRepository.findById(idInteger.longValue());

        User gitHubUser;
        if (optionalUser.isPresent()) {
            gitHubUser = optionalUser.get();

        } else {

            gitHubUser = new User();
            gitHubUser.setId(Long.valueOf(idInteger));
            String fullName = (String) attributes.get("name");
            gitHubUser.setFullName(fullName);

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



/*
    private void updateUser(User gitUser) {
    logger.info("User detected, {}, {}", gitUser.getFullName(), gitUser.getId());

    if (gitUser == null) {
        logger.info("New user detected, {}, {}", gitUser.getFullName(), gitUser.getId());
        gitUser = new User();
    }
    userRepository.save(gitUser);
}*/

}
