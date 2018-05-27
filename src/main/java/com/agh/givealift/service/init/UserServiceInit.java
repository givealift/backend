package com.agh.givealift.service.init;


import com.agh.givealift.model.entity.GalUser;
import com.agh.givealift.model.request.SignUpUserRequest;
import com.agh.givealift.repository.UserRepository;
import com.stefanik.cod.controller.COD;
import com.stefanik.cod.controller.CODFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.GregorianCalendar;
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
        Calendar calendar = new GregorianCalendar(1996, 1, 28, 13, 24, 56);
        newUser.setRate(0D);
        newUser.setBirthDate(calendar.getTime());
        newUser.setRole("USER");
        newUser = userRepository.save(newUser);

        cod.i(newUser);
        return newUser.getGalUserId();
    }

}
