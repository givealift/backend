package com.agh.givealift.service;

import com.agh.givealift.model.entity.GalUser;
import com.agh.givealift.model.request.SignUpUserRequest;
import com.agh.givealift.model.response.GalUserPublicResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserService {
    GalUser getUserByUsername(String username);

    Optional<GalUser> getUserById(long id);

    GalUserPublicResponse getUserPublicInfo(long id);

    List<GalUser> list();

    Long signUp(SignUpUserRequest signUpUserRequest);

    long saveUserPhoto(long id, MultipartFile file);

    byte[] getUserPhoto(long id);

    Long editUser(SignUpUserRequest signUpUserRequest, long id);

    long editUserPassword(String password, long id);

    void removeAll();
}
