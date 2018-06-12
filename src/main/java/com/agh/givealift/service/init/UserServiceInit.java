package com.agh.givealift.service.init;


import com.agh.givealift.model.entity.GalUser;
import com.agh.givealift.model.request.SignUpUserRequest;
import com.agh.givealift.repository.UserRepository;
import com.stefanik.cod.controller.COD;
import com.stefanik.cod.controller.CODFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceInit {
    private static final COD cod = CODFactory.get();
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private Map<String, String> photos = new LinkedHashMap<String, String>() {{
        put("user1@gmail.pl", "logo.png");
        put("user2@gmail.pl", "logo.png");
        put("mw@gmail.pl", "user.png");
        put("bg@gmail.pl", "user.png");
        put("ks@gmail.pl", "Kot.jpg");
        put("ds@gmail.pl", "user3.png");
        put("pz@gmail.pl", "user3.png");
        put("jo@gmail.pl", "user2.png");


    }};

    @Autowired
    public UserServiceInit(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public GalUser getUserByUsername(String username) {
        return userRepository.findByEmail(username);
    }

    public Optional<GalUser> getUserById(long id) {
        return userRepository.findById(id);
    }

    public Long signUp(SignUpUserRequest signUpUserRequest) {
        GalUser newUser = new GalUser();
        signUpUserRequest.mapToGalUserWithoutPassword(newUser);
        newUser.setPassword(passwordEncoder.encode(signUpUserRequest.getPassword()));
        newUser.setRateAmount(0L);
        Path path = Paths.get(photos.get(signUpUserRequest.getEmail()));
        byte[] data = null;
        try {
            data = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        newUser.setPhoto(data);
        newUser.setRole("USER");
        newUser = userRepository.save(newUser);

        cod.i(newUser);
        return newUser.getGalUserId();
    }

}
