package com.agh.givealift.service;

import com.agh.givealift.model.entity.GalUser;
import com.agh.givealift.model.request.SignUpUserRequest;
import com.agh.givealift.model.response.GalUserPublicResponse;

import java.util.List;

public interface UserService {
    GalUser getUserByUsername(String username);

    GalUser getUserById(long id);

    GalUserPublicResponse getUserPublicInfo(long id);

    List<GalUser> list();

    Long signUp(SignUpUserRequest signUpUserRequest);

    Long editUser(SignUpUserRequest signUpUserRequest, long id);

    long editUserPassword(String password, long id);
}
