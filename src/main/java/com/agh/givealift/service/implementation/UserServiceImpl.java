package com.agh.givealift.service.implementation;


import com.agh.givealift.model.entity.GalUser;
import com.agh.givealift.model.request.SignUpUserRequest;
import com.agh.givealift.model.response.GalUserPublicResponse;
import com.agh.givealift.repository.UserRepository;
import com.agh.givealift.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<GalUser> getUserByUsername(String username) {
        return Optional.ofNullable(userRepository.findByEmail(username));
    }


    public long saveUserPhoto(long id, MultipartFile file) {
        GalUser user = userRepository.getOne(id);
        try {
            user.setPhoto(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        userRepository.save(user);
        return user.getGalUserId();
    }

    @Override
    public byte[] getUserPhoto(long id) {

        return userRepository.getOne(id).getPhoto();
    }

    @PreAuthorize("permitAll()")
    public GalUserPublicResponse getUserPublicInfo(long id) {
        return new GalUserPublicResponse(userRepository.getOne(id));
    }

    // @PreAuthorize("#id==principal.user.galUserId")
    public Optional<GalUser> getUserById(long id) {
        return userRepository.findById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<GalUser> list() {
        return userRepository.findAll();
    }

    @PreAuthorize("isAnonymous()")
    public Long signUp(SignUpUserRequest signUpUserRequest) {
        GalUser newUser = new GalUser();
        signUpUserRequest.mapToGalUserWithoutPassword(newUser);
        newUser.setPassword(passwordEncoder.encode(signUpUserRequest.getPassword()));
        newUser.setRateAmount(0L);
        newUser.setRole("USER");
        return userRepository.save(newUser).getGalUserId();
    }

    @PreAuthorize("#id==principal.user.galUserId")
    public Long editUser(SignUpUserRequest signUpUserRequest, long id) {
        GalUser user = userRepository.getOne(id);
        signUpUserRequest.mapToGalUserWithoutPassword(user);
        return userRepository.save(user).getGalUserId();
    }

    // @PreAuthorize("#id==principal.user.galUserId")
    public long resetUserPassword(String password, long id) {
        GalUser user = userRepository.getOne(id);
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user).getGalUserId();
    }


    public void removeAll() {
        userRepository.deleteAll();
    }

    @PreAuthorize("isAuthenticated()")
    public Double changeRate(Integer rate, long id) {
        GalUser user = userRepository.getOne(id);
        double newRate;
        if (user.getRateAmount() == 0) {
            user.setRate((double) rate);
            user.setRateAmount(1L);
            newRate = rate;
        } else {
            newRate = ((user.getRate() * user.getRateAmount()) + rate) / (user.getRateAmount() + 1);
            user.setRate((newRate));
            user.setRateAmount(user.getRateAmount() + 1);
        }
        userRepository.save(user);
        return newRate;
    }

    @Override
    public void changeUserPassword(GalUser user, String oldPass, String newPass) throws AuthenticationException {
        if (!passwordEncoder.matches(oldPass, user.getPassword())) {
            throw new AuthenticationException();
        } else {
            user.setPassword(passwordEncoder.encode(newPass));
            userRepository.save(user);
        }

    }
}
