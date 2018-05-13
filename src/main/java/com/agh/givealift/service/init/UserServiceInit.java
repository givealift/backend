package com.agh.givealift.service.init;


import com.agh.givealift.model.entity.GalUser;
import com.agh.givealift.model.request.SignUpUserRequest;
import com.agh.givealift.model.response.GalUserPublicResponse;
import com.agh.givealift.repository.UserRepository;
import com.stefanik.cod.controller.COD;
import com.stefanik.cod.controller.CODFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceInit {
    private static final COD cod = CODFactory.get();
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

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
        newUser.setRole("USER");
        newUser = userRepository.save(newUser);

        cod.i(newUser);
        return newUser.getGalUserId();
    }

}
