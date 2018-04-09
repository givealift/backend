package com.agh.givealift.service;


import com.agh.givealift.model.request.LoginUser;
import com.agh.givealift.model.entity.GalUser;
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

    public List<GalUser> list() {
        return userRepository.findAll();
    }

    public void signUp(LoginUser loginUser) {
        userRepository.save(new GalUser(loginUser.getUsername(), passwordEncoder.encode(loginUser.getPassword())));
    }
}
