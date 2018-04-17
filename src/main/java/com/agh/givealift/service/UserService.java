package com.agh.givealift.service;


import com.agh.givealift.model.entity.GalUser;
import com.agh.givealift.model.request.SignUpUserRequest;
import com.agh.givealift.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public GalUser getUserByUsername(String username) {
        return userRepository.findByLogin(username);
    }

    public GalUser getUserById(long id) {
        return userRepository.getOne(id);
    }


    public List<GalUser> list() {
        return userRepository.findAll();
    }

    public Long signUp(SignUpUserRequest signUpUserRequest) {
        GalUser newUser = signUpUserRequest.mapToGalUserWithoutPassword();
        newUser.setPassword(passwordEncoder.encode(signUpUserRequest.getPassword()));
        return userRepository.save(newUser).getGalUserId();
    }
}
