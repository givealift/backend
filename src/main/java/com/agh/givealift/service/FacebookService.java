package com.agh.givealift.service;

import com.agh.givealift.exceptions.FacebookAccessException;
import com.agh.givealift.model.entity.GalUser;
import com.agh.givealift.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.UUID;

@Service
public class FacebookService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    public String createFacebookAuthorizationURL() {
        FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory("2164182813811928", "bcb493418f271307ae9bdbaca401483f");
        OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
        OAuth2Parameters params = new OAuth2Parameters();
        params.setRedirectUri("https://localhost:8443/api/facebook");
        params.setScope("public_profile,email,user_birthday");
        return oauthOperations.buildAuthorizeUrl(params);
    }


    public GalUser createFacebookAccessToken(String code) throws FacebookAccessException {
        String accessToken;
        AccessGrant accessGrant = null;
        FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory("2164182813811928", "bcb493418f271307ae9bdbaca401483f");
        try {
            accessGrant = connectionFactory.getOAuthOperations()
                    .exchangeForAccess(code, "https://localhost:8443/api/facebook", null);
        } catch (HttpClientErrorException e) {
            throw new FacebookAccessException();
        }

        accessToken = accessGrant.getAccessToken();
        Facebook facebook = new FacebookTemplate(accessToken);
        String[] fields = {"id", "email", "first_name", "last_name"};

        return findOrCreateUser(facebook.fetchObject("me", org.springframework.social.facebook.api.User.class, fields));


    }

    private GalUser findOrCreateUser(org.springframework.social.facebook.api.User facebookUser) {
        GalUser user = userRepository.findByFacebookId(facebookUser.getId());
        if (user == null) return createUser(facebookUser);
        return user;
    }


    private GalUser createUser(User facebookUser) {
        GalUser user = new GalUser();
        user.setLogin(facebookUser.getEmail());
        user.setFacebookId(facebookUser.getId());
        user.setPassword(passwordEncoder.encode(generateString()));
        userRepository.save(user);
        userRepository.flush();
        return user;
    }


    private static String generateString() {
        String uuid = UUID.randomUUID().toString();
        return "uuid = " + uuid;
    }

}
